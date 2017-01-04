# BayesClassifieronHadoop
##组织结构
###com.chensq.bayesclassifier.ClassifyModel 是由训练结果构建的概率统计模型，在测试时直接调用这个模型即可
###com.chensq.bayesclassifier.test 是测试模块
###com.chensq.bayesclassifier.train.clzdoc 用于统计每个类对应的文档总数
###com.chensq.bayesclassifier.train.clzterm 用于统计每个类中每个单词的出现总数
###com.chensq.bayesclassifier.DataAnalisy.java 用于计算测试结果的每个类的准确率和召回率以及平均值
###com.chensq.bayesclassifier.Main.java 是程序入口，通过解析入口参数启动相应作业
###com.chensq.bayesclassifier.writables 是自定义的writable

hdfs dfs -rm -r /bayes/clzoutput
hadoop jar /home/chensq/IdeaProjects/BayesClassifier/out/artifacts/BayesClassifier_jar/BayesClassifier.jar trainclz /bayes/clzoutput /bayes/input/train/CHINA /bayes/input/train/UK 

hdfs dfs -rm -r /bayes/termoutput
hadoop jar /home/chensq/IdeaProjects/BayesClassifier/out/artifacts/BayesClassifier_jar/BayesClassifier.jar trainterm /bayes/termoutput /bayes/input/train/CHINA /bayes/input/train/UK 

hadoop jar /home/chensq/IdeaProjects/BayesClassifier/out/artifacts/BayesClassifier_jar/BayesClassifier.jar test /bayes/testoutput /bayes/input/test/CHINA /bayes/input/test/UK 

hdfs dfs -ls /bayes
