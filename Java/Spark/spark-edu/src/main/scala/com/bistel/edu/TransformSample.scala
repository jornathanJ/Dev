package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;

object TransformSample extends App {
  val conf = new SparkConf().setAppName("TransformSample").setMaster("local")
  val sc = new SparkContext(conf)
  val rdd = sc.parallelize(1 to 10)
  val a = rdd.sample(false, 1, 1).collect
  val b = rdd.sample(true, 1, 1).collect
  println("A==========")
  a.foreach(println)
  println("B==========")
  b.foreach(println)
}