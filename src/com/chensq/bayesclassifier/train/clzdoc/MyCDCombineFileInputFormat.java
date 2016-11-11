package com.chensq.bayesclassifier.train.clzdoc;

import com.chensq.bayesclassifier.train.clzterm.MyCombineFileRecordReader;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileRecordReader;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;

import java.io.IOException;

/**
 * Created by chensq on 16-11-9.
 */
public class MyCDCombineFileInputFormat extends org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat<LongWritable,Text>{

    @Override
    public RecordReader<LongWritable, Text> createRecordReader(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException {
        CombineFileSplit combineFileSplit=(CombineFileSplit)inputSplit;
        CombineFileRecordReader<LongWritable,Text> recordReader=new CombineFileRecordReader<>(combineFileSplit,taskAttemptContext,MyCombineFileRecordReader.class);

        try {
            recordReader.initialize(combineFileSplit,taskAttemptContext);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return recordReader;
    }
}
