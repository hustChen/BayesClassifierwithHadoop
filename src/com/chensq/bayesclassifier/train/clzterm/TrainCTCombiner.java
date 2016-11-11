package com.chensq.bayesclassifier.train.clzterm;

import com.chensq.bayesclassifier.writables.TrainWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by chensq on 16-11-9.
 */
public class TrainCTCombiner extends Reducer<TrainWritable,IntWritable,TrainWritable,IntWritable> {
    @Override
    protected void reduce(TrainWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count=0;
        for(IntWritable i:values){
            count+=i.get();
        }

        context.write(key,new IntWritable(count));
    }
}
