package com.chensq.bayesclassifier.writables;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by chensq on 16-11-11.
 */
public class TestWritable implements Writable{
    IntWritable count;
    Text first;
    Text second;

    public TestWritable(int count,String frist,String second){
        this.count=new IntWritable(count);
        this.first=new Text(first);
        this.second=new Text(second);
    }

    public TestWritable(IntWritable count,Text first,Text second){
        this.count=count;
        this.first=first;
        this.second=second;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        count.write(dataOutput);
        first.write(dataOutput);
        second.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        count.readFields(dataInput);
        first.readFields(dataInput);
        second.readFields(dataInput);
    }
}
