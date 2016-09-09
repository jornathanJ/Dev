package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object ActionCollectAsMap extends App {

  val conf = new SparkConf().setAppName("ActionCollectAsMap").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val a = sc.parallelize(List(1, 2, 1, 3), 1)
  val b = sc.parallelize(List(1, 2, 4, 3), 1)
  val c = a.zip(b)
  val z = c.collectAsMap // Map(2 -> 2, 1 -> 4, 3 -> 3)
  println("C===============")
  c.foreach(println)
  println("Z===============")
  z.foreach(println)

}