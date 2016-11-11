package com.chensq.bayesclassifier.test;

import com.chensq.bayesclassifier.ClassifyModel.BayesClassifyModel;
import com.chensq.bayesclassifier.writables.TestWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chensq on 16-11-11.
 */
public class TestReducer extends Reducer<Text,TestWritable,Text,Text>{

    BayesClassifyModel bayesClassifyModel;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        String clzPath="hdfs://192.168.25.202:9000/bayes/clzoutput/part-r-00000";
        String termPath="hdfs://192.168.25.202:9000/bayes/termoutput/part-r-00000";
        bayesClassifyModel=new BayesClassifyModel(clzPath,termPath);
    }

    @Override
    protected void reduce(Text key, Iterable<TestWritable> values, Context context) throws IOException, InterruptedException {
        Map<String,Integer> termsmap=new HashMap<>();
        String clz="";
        for(TestWritable t:values){
            clz=t.getSecond().toString();
            Integer i=termsmap.get(t.getFirst().toString());
            if(i==null)
                termsmap.put(t.getFirst().toString(),t.getCount().get());
            else{
                termsmap.put(t.getFirst().toString(),i+t.getCount().get());
            }
        }

        String result=bayesClassifyModel.classify(termsmap);

        String str=String.format("%s %s %s",key.toString(),clz,result);

        context.write(new Text(str),new Text(""));


    }
}
