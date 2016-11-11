package com.chensq.bayesclassifier.train.clzterm;

import com.chensq.bayesclassifier.writables.TrainWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;

/**
 * Created by chensq on 16-11-9.
 */
public class ClassTermRunTool extends Configured implements Tool {
    @Override
    public int run(String[] strings) throws Exception {
        Configuration conf=new Configuration();
        Job job=Job.getInstance(conf);
        job.setJobName("class-term-training");

        job.setInputFormatClass(MyCombineFileInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        job.setOutputKeyClass(TrainWritable.class);
        job.setOutputValueClass(IntWritable.class);

        job.setMapperClass(TrainCTMapper.class);
        job.setCombinerClass(TrainCTCombiner.class);
        job.setReducerClass(TrainCTReducer.class);

        FileInputFormat.setInputPaths(job,new Path(strings[0]));
        FileOutputFormat.setOutputPath(job,new Path(strings[1]));

        return job.waitForCompletion(true)?0:1;
    }
}
