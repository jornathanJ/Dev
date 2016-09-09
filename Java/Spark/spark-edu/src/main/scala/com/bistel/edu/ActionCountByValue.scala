

package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object ActionCountByValue extends App {

  val conf = new SparkConf().setAppName("ActionCountByValue").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val rdd = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 2, 4, 2, 1, 1, 1, 1, 1))
  val x = rdd.countByValue //Map(5 -> 1, 8 -> 1, 3 -> 1, 6 -> 1, 1 -> 6, 2 -> 3, 4 -> 2, 7 ->1)
  println("X======================")
  println(x)

}