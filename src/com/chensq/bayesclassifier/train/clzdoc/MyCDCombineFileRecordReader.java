package com.chensq.bayesclassifier.train.clzdoc;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

import java.io.IOException;

/**
 * Created by chensq on 16-11-9.
 * 由于只需要统计当前类的文档总数
 * 所以每个分片只需要访问一次就行了，在这一次访问获取当前文档的文件名（doc名）和上一级目录名，也就是class名
 */
public class MyCDCombineFileRecordReader extends RecordReader<Text,Text>{

    private CombineFileSplit combineFileSplit;
    private LineRecordReader lineRecordReader=new LineRecordReader();
    private Path[] paths;
    private int totalLength;
    private int currentIndex;
    private float currentProgress=0;
    private Text currentKey;
    private Text currentValue;
    private boolean isreaded=false;//用于表示当前分片是否被访问过


    public MyCDCombineFileRecordReader(CombineFileSplit combineFileSplit, TaskAttemptContext taskAttemptContext, Integer index){
        super();
        this.combineFileSplit=combineFileSplit;
        this.currentIndex=index;
    }


    @Override
    public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        this.combineFileSplit=(CombineFileSplit)inputSplit;
        FileSplit fileSplit=new FileSplit(combineFileSplit.getPath(currentIndex),combineFileSplit.getOffset(currentIndex),combineFileSplit.getLength(currentIndex),combineFileSplit.getLocations());
        lineRecordReader.initialize(fileSplit,taskAttemptContext);

        this.paths=combineFileSplit.getPaths();
        totalLength=paths.length;
        taskAttemptContext.getConfiguration().set("map.input.file.name",combineFileSplit.getPath(currentIndex).getName());
        taskAttemptContext.getConfiguration().set("map.input.file.dir",combineFileSplit.getPath(currentIndex).getParent().getName());
        currentKey=new Text(combineFileSplit.getPath(currentIndex).getParent().getName());
        currentValue=new Text(combineFileSplit.getPath(currentIndex).getName());
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if(currentIndex>=0&&currentIndex<totalLength&&!isreaded) {
            isreaded=true;
            return true;//第一次被访问，返回true
        }
        return false;//已经被访问过了，所以直接返回false，处理下一个分片。这个返回值决定了combilefileinputformat处理文件的索引，可以查看combinefilerecordreader的源码
    }

    @Override
    public Text getCurrentKey() throws IOException, InterruptedException {
        //currentKey=new Text()
        return currentKey;
    }

    @Override
    public Text getCurrentValue() throws IOException, InterruptedException {
        //currentValue=lineRecordReader.getCurrentValue();
        return currentValue;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        if(currentIndex>=0&&currentIndex<totalLength)
            currentProgress=(float)currentIndex/totalLength;
        return currentProgress;
    }

    @Override
    public void close() throws IOException {
        lineRecordReader.close();
    }
}
