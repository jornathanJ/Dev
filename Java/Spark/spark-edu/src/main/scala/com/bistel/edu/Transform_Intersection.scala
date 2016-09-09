package com.bistel.edu

import org.apache.spark.{TaskContext,SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._

/**
 * @author TED
 */
object Transform_Intersection {
  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("Transform_Intersection").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val x = sc.parallelize(1 to 9)
    val y = sc.parallelize(6 to 19)
    
    val z = x.intersection(y)
    z.collect().foreach { println }
    println("=========")
    val w = x.intersection(y, 2)
    w.collect().foreach { println }
    
    def myfunc(index: Int, iter: Iterator[Int]): Iterator[String] = {
    iter.toList.map(x => index + "," + x).iterator
   }
    
    val r = x.mapPartitionsWithIndex(myfunc).collect // Array(0,1, 0,2, 1,3, 1,4, 1,5, 2,6,2,7, 3,8, 3,9, 3,10)
    r.foreach(println)
    
    sc.stop()
  }
}