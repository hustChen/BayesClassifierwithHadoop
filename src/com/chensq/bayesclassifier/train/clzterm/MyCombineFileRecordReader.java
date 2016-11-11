package com.chensq.bayesclassifier.train.clzterm;

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
 */
public class MyCombineFileRecordReader extends RecordReader<LongWritable,Text>{

    private CombineFileSplit combineFileSplit;
    private LineRecordReader lineRecordReader=new LineRecordReader();
    private Path[] paths;
    private int totalLength;
    private int currentIndex;
    private float currentProgress=0;
    private LongWritable currentKey;
    private Text currentValue;


    public MyCombineFileRecordReader(CombineFileSplit combineFileSplit,TaskAttemptContext taskAttemptContext,Integer index){
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
        taskAttemptContext.getConfiguration().set("map.input.file.dir",combineFileSplit.getPath(currentIndex).getParent().getName());
        taskAttemptContext.getConfiguration().set("map.input.file.name",combineFileSplit.getPath(currentIndex).getName());
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if(currentIndex>=0&&currentIndex<totalLength)
            return lineRecordReader.nextKeyValue();
        return false;
    }

    @Override
    public LongWritable getCurrentKey() throws IOException, InterruptedException {
        currentKey=lineRecordReader.getCurrentKey();
        return currentKey;
    }

    @Override
    public Text getCurrentValue() throws IOException, InterruptedException {
        currentValue=lineRecordReader.getCurrentValue();
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
