package com.chensq.bayesclassifier.ClassifyModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/10.
 */
public class BayesClassifyTest {
    public static void main(String[] args){
        String clz_path="clz.txt";
        String term_path="terms.txt";
        Map<String,Integer> term_map=new HashMap<>();
        term_map.put("Chinese",3);
        term_map.put("Tokyo",1);
        term_map.put("Japan",2);
        BayesClassifyModel model=new BayesClassifyModel(clz_path,term_path);
        String result=model.classify(term_map);
        System.out.println(result);

    }
}
