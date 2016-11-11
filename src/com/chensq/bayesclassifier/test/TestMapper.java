package com.chensq.bayesclassifier.test;

import com.chensq.bayesclassifier.writables.TestWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by chensq on 16-11-11.
 */
public class TestMapper extends Mapper<LongWritable,Text,TestWritable,TestWritable> {

    private final static int ONE=1;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String filename=context.getConfiguration().get("map.input.file.name");
        String clzname=context.getConfiguration().get("map.input.file.dir");

        TestWritable testWritable=new TestWritable(ONE,value.toString(),clzname);
        TestWritable out_key=new TestWritable(ONE,value.toString(),filename);
        context.write(out_key,testWritable);


    }
}
