package com.chensq.bayesclassifier.writables;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by chensq on 16-11-11.
 */
public class TestWritable implements WritableComparable<TestWritable>{
    private IntWritable count;
    private Text first;

    public IntWritable getCount() {
        return count;
    }

    public void setCount(IntWritable count) {
        this.count = count;
    }

    public Text getFirst() {
        return first;
    }

    public void setFirst(Text first) {
        this.first = first;
    }

    public Text getSecond() {
        return second;
    }

    public void setSecond(Text second) {
        this.second = second;
    }

    private Text second;

    public TestWritable(){
        count=new IntWritable();
        first=new Text();
        second=new Text();
    }

    public TestWritable(int count,String first,String second){
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

    @Override
    public int compareTo(TestWritable o) {
        if(this.first.compareTo(o.first)==0)
            return this.second.compareTo(o.second);
        else{
            return this.first.compareTo(o.first);
        }
    }
}
