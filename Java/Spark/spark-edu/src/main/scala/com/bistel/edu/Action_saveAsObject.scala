package com.bistel.edu

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._

/**
 * @author TED
 */
object Action_saveAsObject {
  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("Action_saveAsObject").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.parallelize(List("tt","rrrr","eee","www","qqq"), 2)
    rdd.saveAsObjectFile("C:/scala-workspace/edu-test-file1")
    val loadRdd = sc.objectFile("C:/scala-workspace/edu-test-file1")
  }
}