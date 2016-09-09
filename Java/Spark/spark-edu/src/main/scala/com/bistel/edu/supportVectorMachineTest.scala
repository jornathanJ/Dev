package com.bistel.edu

import org.apache.spark.mllib.classification.SVMWithSGD
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics

//import org.apache.spark.{SparkConf, SparkContext}
/**
 * @author TED
 */
object supportVectorMachineTest{
  
   def main(args : Array[String]){
    
     val sparkConf = new SparkConf().setAppName("supportVectorMachineTest").setMaster("local[*]")
     val sc = new SparkContext(sparkConf)
    
     val data = MLUtils.loadLibSVMFile(sc, "C:/spark-1.5.0-bin-hadoop2.6/README.md")
     val splits = data.randomSplit(Array(0.6, 0.4))
     val training = splits(0).cache()
     val test = splits(1)
     val numIteratorions = 100
     val model = SVMWithSGD.train(training, numIteratorions)
     val scoreAndlabels = test.map{ point =>
      val score = model.predict(point.features)
      (score, point.label)
     }
    
     val metrics = new BinaryClassificationMetrics(scoreAndlabels)
    
     println("area under ROC= " + metrics.areaUnderROC())
     println("area under PR= " + metrics.areaUnderPR())
    
     sc.stop()
   }
  
}