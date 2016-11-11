package com.chensq.bayesclassifier.train.clzterm;

import com.chensq.bayesclassifier.writables.TrainWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by chensq on 16-11-9.
 */
public class TrainCTReducer extends Reducer<TrainWritable,IntWritable,Text,Text>{

    @Override
    protected void reduce(TrainWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count=0;
        for(IntWritable i:values){
            count+=i.get();
        }

        String output_key=String.format("%s %s %d",key.second,key.first,count);
        context.write(new Text(output_key),new Text(""));
    }
}
