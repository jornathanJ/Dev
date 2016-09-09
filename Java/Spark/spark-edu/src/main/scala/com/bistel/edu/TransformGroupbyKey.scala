package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;

object TransformGroupbyKey extends App{

  val conf = new SparkConf().setAppName("TransformGroupbyKey").setMaster("local[*]")
  val sc = new SparkContext(conf)  
  val x = sc.parallelize(1 to 10)
  
  //Same Result
  val y = x.keyBy(_% 2)  
  val z = x.groupBy(_% 2).collect
  
  val k = x.keyBy(_% 2).groupByKey.collect
  
  
  println("Y============================")
  y.foreach(println)
  
  println("Z============================")
  z.foreach(println)
  
  
  println("K============================")
  k.foreach(println)
  
  
}












