package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object TransformSortByKey extends App {
  def printnum(key: String, x: Any) {
    print(key)
    println("======================")
    println(x)
  }
  val conf = new SparkConf().setAppName("TransformSortByKey").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val a = sc.parallelize(List("dog", "cat", "owl", "gnu", "ant"), 2)
  a.foreach(println)
  val b = sc.parallelize(1 to a.count.toInt, 2)
  val c = a.zip(b)
  val x = c.sortByKey(true).collect //Array((ant,5), (cat,2), (dog,1), (gnu,4), (owl,3))
  val y = c.sortByKey(false).collect // Array((owl,3), (gnu,4), (dog,1), (cat,2), (ant,5))
  println("X======================")
  x.foreach(println)
  println("Y======================")
  y.foreach(println)
}