package com.bistel.edu

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._

/**
 * @author TED
 */
object Transform_AggregateByKey {
  def printnum(key:String,x:Any) {
    print(key)
    println("===========")
    println(x)
  }
  
  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("Transform_AggregateByKey").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.parallelize(List(1,2,3,4,5,6), 2)
    val a = rdd.map(x => (x%2,x))
    val b = rdd.aggregate(0)(math.max(_, _), _ + _)
    
    sc.stop()
  }
}