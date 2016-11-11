package com.chensq.bayesclassifier.train.clzterm;

import com.chensq.bayesclassifier.writables.TrainWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Created by chensq on 16-11-9.
 */
public class TrainCTPartitioner extends Partitioner<TrainWritable,IntWritable> {

    @Override
    public int getPartition(TrainWritable trainWritable, IntWritable intWritable, int i) {
        return 0;
    }
}
