package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;

import org.apache.spark.TaskContext;

object Transform_ReduceByKey extends App {
   
  val conf = new SparkConf().setAppName("Transform_ReduceByKey").setMaster("local[*]")
  val sc = new SparkContext(conf)
  
  //val rdd = sc.parallelize(1 to 2, 1)
  val rdd = sc.parallelize(1 to 10, 3)
  val x = rdd.map(x => ((x%2), x)).collect
  val k = rdd.map(x => ((x%2), x))
  
  println("x============================")
  x.foreach(println)
  
  val y = k.reduceByKey(_+_)
  println("Y============================")
  y.foreach(println)
  
  
}