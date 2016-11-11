package com.chensq.bayesclassifier.test;

import com.chensq.bayesclassifier.writables.TestWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by chensq on 16-11-11.
 */
public class TestCombiner extends Reducer<TestWritable,TestWritable,Text,TestWritable> {

    @Override
    protected void reduce(TestWritable key, Iterable<TestWritable> values, Context context) throws IOException, InterruptedException {
        int count=0;
        String term="";
        String clz="";
        for(TestWritable testWritable:values){
            count+=testWritable.getCount().get();
            term=testWritable.getFirst().toString();
            clz=testWritable.getFirst().toString();
        }

        Text combine_key=key.getFirst();
        TestWritable combine_value=new TestWritable(count,term,clz);

        context.write(combine_key,combine_value);
    }
}
