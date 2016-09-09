package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;

import org.apache.spark.TaskContext;


object TransfromCogroup extends App {
 
  
  val conf = new SparkConf().setAppName("TransfromCogroup").setMaster("local[*]")
  val sc = new SparkContext(conf)
  
  
  val a = sc.parallelize(List(1, 2, 1, 3), 1)
  val aa = a.map((_, "a"))
  val b = a.map((_, "b"))
  val c = a.map((_, "c"))  
  val x = b.cogroup(c).collect
    
  val d = a.map((_, "d"))
  val y = b.groupWith(c, d, aa).collect
  
  println("B=================")
  b.foreach(println)
  
  println("X=================")
  x.foreach(println)
  
  println("Y=================")
  y.foreach(println)
  
}