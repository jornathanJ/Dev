package com.bistel.edu

import org.apache.spark.{TaskContext,SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._
import scala.collection.IterableLike

/**
 * @author TED
 */
object Transform_MapPartition {
  def myfunc[T](iter:Iterator[T]) : Iterator[(T,T)] ={ 
    val tc = TaskContext.get()
    //println("Partition ID : %s, Attemp ID : %s"
    var res = List[(T,T)]()
    var pre = iter.next()
    while(iter.hasNext){
      val cur = iter.next()
      res .::= (pre,cur)
      //println(">>"+res)
      pre = cur
    }
    res.iterator
  }
  
  //패러럴이 있어서 여러번 실행된다.
  def main(args: Array[String]) : Unit ={
    val sparkConf = new SparkConf().setAppName("Transform_MapPartition").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.parallelize(1 to 9, 3)
    rdd.mapPartitions(myfunc).collect()//.foreach(println)
    rdd.foreach(println)
    
    sc.stop()
  }
}