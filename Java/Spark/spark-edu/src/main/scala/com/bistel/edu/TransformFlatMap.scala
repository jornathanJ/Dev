package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;

object TransformFlatMap extends App{

  val conf = new SparkConf().setAppName("TransformFlatMap").setMaster("local[*]")
  val sc = new SparkContext(conf)
  
  val rdd = sc.parallelize(1 to 3)
  
  rdd.foreach(println)
  
  val a = rdd.flatMap(1 to _).collect
  val b = rdd.map(x => x).collect // when rdd is Array ---> map( r => r(1) )
  val c = rdd.map(x => List(x)).collect
  val d = rdd.flatMap(x => List(x)).collect
  //val e = rdd.flatMap(_*2).collect
  
  
  
  println("A============================")
  a.foreach(println)
  println("B============================")
  b.foreach(println)
  println("C============================")
  c.foreach(println)
  println("D============================")
  d.foreach(println)
  
  sc.stop()
}












