package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;

object TransformFilter extends App {
  val conf = new SparkConf().setAppName("TransformFilter").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val rdd = sc.parallelize(1 to 5)
  val a = rdd.filter(_ %2!=0).collect
  a.foreach(println)
}
