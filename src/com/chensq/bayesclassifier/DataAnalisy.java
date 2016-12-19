package com.chensq.bayesclassifier;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Created by chensq on 16-12-17.
 */
public class DataAnalisy {
    public static void main(String[] args) throws IOException {
        String outputpath="hdfs://master:9000/bayes/testoutput/part-r-00000";
        FileSystem fs= FileSystem.get(URI.create(outputpath),new Configuration());
        FSDataInputStream fsDataInputStream=fs.open(new Path(outputpath));
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(fsDataInputStream));
        String line;

        float TP,TN,FP,FN;
        TN=TP=FN=FP=0;
        String positive=null;

        while((line=bufferedReader.readLine())!=null){
            String[] paras=line.split(" ");
            String trueresult=paras[1];
            String predict=paras[2];

            if(positive==null)
                positive=trueresult;
            if(trueresult.compareTo(positive)==0){
                if(predict.compareTo(trueresult)==0)
                    TP++;
                else
                    FP++;
            }else{
                if(predict.compareTo(trueresult)==0){
                    TN++;
                }else
                    FN++;
            }

        }

        float pp=TP/(TP+FP);
        float pn=TN/(TN+FN);
        float rp=TP/(TP+FN);
        float rn=(TN)/(TN+FP);
        StringBuilder sb=new StringBuilder("");
        sb.append(String.format("positive:%s \n TP:%f FP:%f TN:%f FN:%f\n",positive,TP,FP,TN,FN));
        sb.append(String.format("Positive Precision: %f ,Positive Recall: %f \n",pp,rp));
        sb.append(String.format("Negative Precision: %f ,Negative Recall: %f\n",pn,rn));
        sb.append(String.format("Average Precision: %f\n",(pn+pp)/2));
        sb.append(String.format("Average Recall: %f\n",(rp+rn)/2));

        System.out.println(sb.toString());
    }
}
