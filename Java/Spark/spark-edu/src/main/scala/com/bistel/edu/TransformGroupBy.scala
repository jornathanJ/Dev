package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;

object TransformGroupBy extends App {
  val conf = new SparkConf().setAppName("TransformGroupBy").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val x = sc.parallelize(1 to 10)
  val y = x.groupBy(x => x % 2 match { case 0 => "even"; case _ => "odd" } ).collect
  println("Y=================")
  y.foreach(println)
}