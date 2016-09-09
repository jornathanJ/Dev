package com.bistel.edu

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._

/**
 * @author TED
 */
object Transform_SortBy {
    def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("Transform_Cartesian").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.parallelize(Array(("H",10),("k",26),("m",1),("u",8),("s",5)))
    val x = rdd.sortBy(c => c._1, true).collect()
    val y = rdd.sortBy(c => c._2, true).collect()
    
    println("x===========");
    x.foreach(println)
    println("y===========");
    y.foreach(println)
    sc.stop()
  }
}