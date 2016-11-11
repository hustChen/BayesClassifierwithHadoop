package com.chensq.bayesclassifier.train.clzdoc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by chensq on 16-11-9.
 */
public class TrainCDReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count=0;
        for(IntWritable i:values){
            count+=i.get();
        }

        context.write(key,new IntWritable(count));
    }
}
