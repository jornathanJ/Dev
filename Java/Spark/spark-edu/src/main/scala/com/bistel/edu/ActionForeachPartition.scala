package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;
import org.apache.spark.{TaskContext,SparkConf,SparkContext}

object ActionForeachPartition extends App {
  val conf = new SparkConf().setAppName("ActionForeachPartition").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val rdd = sc.parallelize(List(1,2,3,4,5,6,7,8,2,4,2,1,1,1,1,1), 3)
  rdd.foreachPartition(x => println(x.reduce(_+_)))
}