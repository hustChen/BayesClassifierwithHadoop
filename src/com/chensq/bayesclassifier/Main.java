package com.chensq.bayesclassifier;

import com.chensq.bayesclassifier.test.TestBayesClassifyToolRunner;
import com.chensq.bayesclassifier.train.clzdoc.ClassDocRunTool;
import com.chensq.bayesclassifier.train.clzterm.ClassTermRunTool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Created by chensq on 16-11-11.
 */
public class Main {

    private final static String TRAIN_CLZ="trainclz";
    private final static String TRAIN_TERM="trainterm";
    private final static String TEST="test";

    public static void main(String[] args) throws Exception {

        String[] args_1=new String[args.length-1];
        for(int i=1;i<args.length;i++){
            args_1[i-1]=args[i];
        }


        if(args[0].compareTo(TRAIN_CLZ)==0) {
            int trainclz = ToolRunner.run(new ClassDocRunTool(), args_1);
        }

        if(args[0].compareTo(TRAIN_TERM)==0){
            int trainterm=ToolRunner.run(new ClassTermRunTool(),args_1);
        }

        if(args[0].compareTo(TEST)==0) {
            int test = ToolRunner.run(new TestBayesClassifyToolRunner(), args_1);
        }
    }
}
