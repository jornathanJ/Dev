package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object TransformMapPartitionsWithIndex extends App {

  def myfunc(index: Int, iter: Iterator[Int]): Iterator[String] = {
    iter.toList.map(x => index + "," + x).iterator
  }
  val conf = new SparkConf().setAppName("TransformMapPartitionsWithIndex").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val rdd = sc.parallelize(1 to 9, 3)
  val r = rdd.mapPartitionsWithIndex(myfunc).collect // Array(0,1, 0,2, 1,3, 1,4, 1,5, 2,6,2,7, 3,8, 3,9, 3,10)
  r.foreach(println)

}