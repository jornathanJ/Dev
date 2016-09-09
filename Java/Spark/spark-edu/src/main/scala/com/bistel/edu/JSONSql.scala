package com.bistel.edu



import org.apache.spark.sql.SQLContext

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;

object JSONSql extends App {
 
  
  val sparkConf = new SparkConf().setAppName("JSONSql").setMaster("local[*]")
  val sc = new SparkContext(sparkConf)
  val sqlContext = new SQLContext(sc)
  val jsonDF = sqlContext.load("E:\\200.Spark\\spark-1.5.0-bin-hadoop2.6\\examples\\src\\main\\resources\\people.json")
  
  jsonDF.show()
  jsonDF.printSchema()
  jsonDF.select("name").show()
  jsonDF.select(jsonDF("name"), jsonDF("age") +1).show()
  jsonDF.filter(jsonDF("age")> 21).show()
  jsonDF.groupBy("age").count().show()
  jsonDF.registerTempTable("people")
  
 // val teen
  
  
}