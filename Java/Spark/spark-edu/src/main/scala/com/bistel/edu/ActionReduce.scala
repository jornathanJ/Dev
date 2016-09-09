package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;

import org.apache.spark.TaskContext;

object ActionReduce extends App {
   
  val conf = new SparkConf().setAppName("ActionReduce").setMaster("local[*]")
  val sc = new SparkContext(conf)
  
  //val rdd = sc.parallelize(1 to 2, 1)
  val rdd = sc.parallelize(1 to 10, 3)
  //val rdd = sc.parallelize(List(4, 6, 10, 1, 2, 3), 5)
  
  rdd.foreach(println)
  
 
  val x = rdd.reduce(_+_)
  //앞이 중간 - Fold Left랑 동일, Partition이 달라지면 마지막 연산이 달라져서 결과 값이 달라질수 있다.
  val y = rdd.reduce(_-_)
  
  println("x============================")
  println(x)
  
  println("y============================")
  println(y)
  
  val z = 10;
  
  //printnum("A", z)
  
}