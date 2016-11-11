package com.chensq.bayesclassifier.ClassifyModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/10.
 */
public class TermModel {
    String term;
    int totalCount=0;
    Map<String,Integer> classmap;

    public TermModel(String term){
        this.term=term;
        classmap=new HashMap<>();
    }

    public void addClass(String clz,int count){
        classmap.put(clz,count);
        totalCount+=count;
    }

    public int getTotalCount(){
        return totalCount;
    }

    @Override
    public String toString() {
        return "TermModel{" +
                "term='" + term + '\'' +
                ", totalCount=" + totalCount +
                ", classmap=" + classmap +
                '}';
    }
}
