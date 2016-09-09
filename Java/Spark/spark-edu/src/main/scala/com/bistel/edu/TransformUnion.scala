package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;

object TransformUnion extends App {
  
  val conf = new SparkConf().setAppName("TransformUnion").setMaster("local[*]")
  val sc = new SparkContext(conf)
  
  val rdd = sc.parallelize(1 to 3)
  val a = rdd.sample(false, 1, 1)
  val b = rdd.sample(true, 1, 1)
  val c = a.union(b).collect
  //(a++b).collect
  
  println("A============================")
  a.foreach(println)
  
  println("B============================")
  b.foreach(println)
  
  println("C============================")
  c.foreach(println)
}