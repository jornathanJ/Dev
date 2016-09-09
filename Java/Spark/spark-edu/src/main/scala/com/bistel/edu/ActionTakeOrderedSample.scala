package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;

object ActionTakeOrderedSample extends App {
  val conf = new SparkConf().setAppName("ActionTakeOrderedSample").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val rdd = sc.parallelize(List("dog", "cat", "ape", "salmon", "gnu"), 2)
  val x = rdd.takeOrdered(2)
  val y = rdd.takeSample(true, 2, 1)

  println("X==============")
  x.foreach(println)
  
  println("Y==============")
  y.foreach(println)
}