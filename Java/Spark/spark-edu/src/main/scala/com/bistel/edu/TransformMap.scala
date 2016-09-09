package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;

object TransformMap extends App{

  val conf = new SparkConf().setAppName("TransformMap").setMaster("local[*]")
  val sc = new SparkContext(conf)
  
  val rdd = sc.parallelize(1 to 5)
  val a = rdd.map(_*2).collect
  val b = rdd.map(x => (x.toString, (x*x*x)))
  
  println("A============================")
  a.foreach(println)
  println("B============================")
  b.foreach(println)
  
  val c = rdd.filter(_%2!=0).collect
  
  println("C============================")
  c.foreach(println)
  
  
  def pr(text:String, xs:Any*){
    println()
    printf("[Result of ]" + text + " ----> ")
    println(xs)
    //println("")
    xs.foreach(println)
  }
 
  pr("rdd.map(_*2)", rdd.map(_*2).collect)
}












