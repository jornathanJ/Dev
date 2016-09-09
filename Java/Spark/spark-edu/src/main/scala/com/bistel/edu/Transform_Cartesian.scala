package com.bistel.edu

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._

/**
 * @author TED
 */
object Transform_Cartesian {
  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("Transform_Cartesian").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val x = sc.parallelize(List(1,2,3))
    val y = sc.parallelize(List(8,9))
    val z = x.cartesian(y).collect()
    
    println("z===========");
    z.foreach(println)
    sc.stop()
  }
}