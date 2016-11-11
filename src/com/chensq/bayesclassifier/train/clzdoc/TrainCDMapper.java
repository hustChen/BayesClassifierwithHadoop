package com.chensq.bayesclassifier.train.clzdoc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by chensq on 16-11-9.
 */
public class TrainCDMapper extends Mapper<Text,Text,Text,IntWritable> {

    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        context.write(key,new IntWritable(1));
    }
}
