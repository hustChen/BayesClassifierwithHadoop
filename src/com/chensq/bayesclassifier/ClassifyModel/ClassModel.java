package com.chensq.bayesclassifier.ClassifyModel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/10.
 */
public class ClassModel {
    int totalCount=0;
    private Map<String,Integer> clz_map;

    public ClassModel(){
        clz_map=new HashMap<>();
    }

    public int getTotalCount(){
        return totalCount;
    }

    public void addClass(String clzname,int count){
        totalCount+=count;
        clz_map.put(clzname,count);
    }

    public Iterator<Map.Entry<String,Integer>> getIterator(){
        return clz_map.entrySet().iterator();
    }

    @Override
    public String toString() {
        return totalCount+"\t"+clz_map.toString();
    }
}
