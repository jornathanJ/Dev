package com.bistel.edu

import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;
import org.apache.spark.SparkConf;

object First extends App {
  val conf = new SparkConf().setAppName("First").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val readme = sc.textFile("E:\\200.Spark\\spark-1.5.0-bin-hadoop2.6\\README.md")
  
  val lineWithSpark = readme.filter(line => line.contains("Spark"))
  val words1 = lineWithSpark.map(_.split(" ")).map( r => r(1) )
  val words2 = lineWithSpark.map(_.split(" "))
  //val words = lineWithSpark.map(_.split(" "))
  
  
  println(words1.toDebugString)
  
  println("words1==========")
  words1.foreach(println)
  println("words2==========")
  words2.foreach(println)
  
  words1.cache()
  
  val apacheCnt = words1.filter(_.contains("a")).count()
  val isCnt = words1.filter(_.contains("b")).count()
  
  println("Line with a : %s, Line with b : %s".format(apacheCnt, isCnt))
}



