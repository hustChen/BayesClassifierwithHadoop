package com.chensq.bayesclassifier.ClassifyModel;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.MapWritable;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/10.
 */
public class BayesClassifyModel {


    private String clzPath;
    private String termPath;
    private ClassModel clzModel;
    private Map<String,TermModel> term_map;
    private Map<String,Integer> clz_term_count;



    public BayesClassifyModel(String clzPath,String termPath){
        this.clzPath=clzPath;
        this.termPath=termPath;
        clzModel=new ClassModel();
        term_map=new HashMap<>();
        clz_term_count=new HashMap<>();
        try {
            initClzModel(clzPath);
            initTermMap(termPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initClzModel(String clzPath) throws IOException {

        long begin =System.currentTimeMillis();

        FileSystem fs=FileSystem.get(URI.create(clzPath),new Configuration());
        FSDataInputStream fsDataInputStream=fs.open(new Path(clzPath));
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(fsDataInputStream));


        String line="";
        while((line=bufferedReader.readLine())!=null){
            String[] paras=line.split("\t");
            clzModel.addClass(paras[0],Integer.parseInt(paras[1]));
        }

       // System.out.println("clzModel initialzed:\t"+clzModel.toString());

        long end=System.currentTimeMillis();
        String str=String.format("clzmodel initialized,time spent: %d ms, clzmodel size:%d",end-begin,clzModel.totalCount);
        System.out.println(str);

    }

    private void initTermMap(String termPath) throws IOException {

        long begin=System.currentTimeMillis();

        FileSystem fs=FileSystem.get(URI.create(termPath),new Configuration());
        FSDataInputStream fsDataInputStream=fs.open(new Path(termPath));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fsDataInputStream));
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            String[] paras = line.trim().split(" ");
            TermModel termModel = term_map.get(paras[0]);
            if (termModel == null) {
                termModel = new TermModel(paras[0]);
                term_map.put(paras[0],termModel);
            }

            termModel.addClass(paras[1], Integer.parseInt(paras[2]));
            int term_count=0;
            Integer i=clz_term_count.get(paras[1]);
            if(i==null){
                clz_term_count.put(paras[1],Integer.parseInt(paras[2]));
            }else{
                clz_term_count.put(paras[1],i+Integer.parseInt(paras[2]));
            }
        }


        long end=System.currentTimeMillis();
        String str=String.format("Termmap initialized,time spent: %d ms, term_map size: %d ,clz_term_count_size: %d",end-begin,term_map.size(),clz_term_count.size());
        System.out.println(str);
       // System.out.println("term_map initialized:\t"+term_map.toString());

        //System.out.println(clz_term_count.toString());
    }
    /*
    * 实际测试过程，用户调用这个方法测试，传入所有关键字列表和关键字出现次数
    * */
    public String classify(Map<String,Integer> terms){
        Map<String,Double> clz_probs=new HashMap<>();
        Iterator<Map.Entry<String,Integer>> iterator=clzModel.getIterator();
        while(iterator.hasNext()){
            Map.Entry<String,Integer> clz=iterator.next();

            double clz_prob=0;
            for(Map.Entry<String,Integer> entry:terms.entrySet()){
                String term=entry.getKey();
                int appearence=entry.getValue();



                int term_clz_appear=1;
                if(term_map.get(term)!=null)
                        term_clz_appear=term_map.get(term).classmap.getOrDefault(clz.getKey(),1);
                double prob=(double)(term_clz_appear)/clz_term_count.get(clz.getKey());
                clz_prob+=appearence*Math.log10(prob);
            }

            clz_prob+=Math.log10((double)clz.getValue()/clzModel.getTotalCount());

            clz_probs.put(clz.getKey(),clz_prob);
        }

        String max_clz="";
        double max_prob=Integer.MIN_VALUE;
        for(Map.Entry<String,Double> entry:clz_probs.entrySet()){
            if(entry.getValue()>max_prob){
                max_clz=entry.getKey();
                max_prob=entry.getValue();
            }
        }

        System.out.println(clz_probs.toString());

        return max_clz+" "+max_prob;
    }



}
