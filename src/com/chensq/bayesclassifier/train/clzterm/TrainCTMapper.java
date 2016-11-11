package com.chensq.bayesclassifier.train.clzterm;

import com.chensq.bayesclassifier.writables.TrainWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * Created by chensq on 16-11-9.
 */
public class TrainCTMapper extends Mapper<LongWritable,Text,TrainWritable,IntWritable> {

    private static final IntWritable ONE=new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String fileName=context.getConfiguration().get("map.input.file.name");
        String clzName=context.getConfiguration().get("map.input.file.dir");

        TrainWritable clz_term=new TrainWritable(clzName,value.toString());
        context.write(clz_term,ONE);



    }
}
