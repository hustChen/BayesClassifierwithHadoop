package com.chensq.bayesclassifier.writables;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by chensq on 16-11-9.
 */
public class TrainWritable implements WritableComparable<TrainWritable> {

    public Text first;
    public Text second;

    public TrainWritable(){
        first=new Text();
        second=new Text();
    }

    public TrainWritable(String first,String second){
        this.first=new Text(first);
        this.second=new Text(second);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        first.write(dataOutput);
        second.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        first.readFields(dataInput);
        second.readFields(dataInput);
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

    @Override
    public int compareTo(TrainWritable o) {

        if(this.first.compareTo(o.first)==0)
            return this.second.compareTo(o.second);
        else{
            return this.first.compareTo(o.first);
        }
    }
}
