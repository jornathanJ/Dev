package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;

object TransformJoin extends App {
  val conf = new SparkConf().setAppName("TransformJoinBy").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val a = sc.parallelize(List("dog", "salmon", "salmon", "rat", "elephant"), 3)
  val b = a.keyBy(_.length)
  val c = sc.parallelize(List("dog", "cat", "gnu", "salmon", "rabbit", "turkey", "wolf",  "bear", "bee"), 3)
  val d = c.keyBy(_.length)
  val w = b.join(d).collect
  val x = b.leftOuterJoin(d)
  val y = b.rightOuterJoin(d)
  val z = b.fullOuterJoin(d)
  println("W==============")
  println(w)
  println("X==============")
  x.foreach(println)
  println("Y==============")
  y.foreach(println)
  println("Z==============")
  z.foreach(println)
}