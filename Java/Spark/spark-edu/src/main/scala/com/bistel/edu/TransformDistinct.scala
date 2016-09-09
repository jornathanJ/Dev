package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object TransformDistinct extends App {

  val conf = new SparkConf().setAppName("TransformDistinct").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val x = sc.parallelize(1 to 9)
  val y = sc.parallelize(6 to 15)
  val z = x.union(y)
  println("Z======================")
  z.foreach(println)
  
  //z.foreach(z => (z+z._1))
  val w = z.distinct()
  println("W======================")
  w.foreach(println)

}