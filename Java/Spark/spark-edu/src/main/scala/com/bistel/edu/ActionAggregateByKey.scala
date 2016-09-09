package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;

import org.apache.spark.TaskContext;

object ActionAggregateByKey extends App {
  
  def printnum(key : String, x :Any){
    
    println(key)
    println("======================")
    println(x)
  }

  val conf = new SparkConf().setAppName("ActionAggregateByKey").setMaster("local[*]")
  val sc = new SparkContext(conf)
  
  
  //val rdd = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7), 2).collect()
  val rdd = sc.parallelize(List(1, 2, 3, 4, 5, 6), 2)
  //rdd1.foreach(println)
  val a =  rdd.map(x =>(x%2,x))
  
  a.foreach(println)
  
  val b = a.aggregateByKey(10)(math.max(_,_), _+_)

  
  println("b======================")
  b.foreach(println)
  
  
}