package com.chensq.bayesclassifier.test;

import com.chensq.bayesclassifier.Main;
import com.chensq.bayesclassifier.train.clzterm.MyCombineFileInputFormat;
import com.chensq.bayesclassifier.writables.TestWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;

/**
 * Created by chensq on 16-11-11.
 */
public class TestBayesClassifyToolRunner extends Configured implements Tool {
    @Override
    public int run(String[] strings) throws Exception {
        Configuration conf=new Configuration();
        Job job=Job.getInstance(conf);
        job.setJobName("test job");
        job.setJarByClass(Main.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(TestWritable.class);


        job.setInputFormatClass(MyCombineFileInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        job.setMapperClass(TestMapper.class);
        //job.setCombinerClass(TestCombiner.class);
        job.setReducerClass(TestReducer.class);

        for(int i=1;i<strings.length;i++){
            FileInputFormat.addInputPath(job,new Path(strings[i]));
        }
        FileOutputFormat.setOutputPath(job,new Path(strings[0]));

        return job.waitForCompletion(true)?0:1;

    }
}
