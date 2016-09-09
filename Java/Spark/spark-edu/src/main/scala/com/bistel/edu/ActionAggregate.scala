package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;

import org.apache.spark.TaskContext;

object ActionAggregate extends App{
  
  def printnum(key : String, x :Any){
    
    println(key)
    println("======================")
    println(x)
  }

  val conf = new SparkConf().setAppName("ActionAggregate").setMaster("local[*]")
  val sc = new SparkContext(conf)
  
  
  val rdd1 = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 3)  
  val a = rdd1.aggregate(0)(math.max(_,_), _+_)

  printnum("A", a)
  
  val rdd4 = sc.parallelize(List("12", "23", "345", ""), 4)  
  val f = rdd4.aggregate("")((x, y) => (math.max(x.length,y.length).toString), (x, y) => x+ y)

  printnum("F", f)
}