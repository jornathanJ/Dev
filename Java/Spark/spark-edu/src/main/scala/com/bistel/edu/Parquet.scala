package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext;
import org.apache.spark.sql.SQLContext
import org.apache.spark.SparkContext._;

object Parquet extends App {
  val conf = new SparkConf().setAppName("Parquet").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)
  val jsonDF = sqlContext.load("people.par", "parquet")
  jsonDF.show()
}