package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;

import org.apache.spark.TaskContext;

object ActionSaveAsTextFile extends App {
   
  val conf = new SparkConf().setAppName("ActionSaveAsTextFile").setMaster("local[*]")


  val sc = new SparkContext(conf)
  val rdd = sc.parallelize(List("dog", "cat", "ape", "salmon", "gnu"), 2)
  
  rdd.saveAsTextFile("C:/edu/spark/rddkS2")
  
  

  rdd.saveAsTextFile("C:/edu/spark/myData_12", classOf[org.apache.hadoop.io.compress.GzipCodec])
  
  
}

//   conf.setCompressMapOutput(true)
//  conf.set("mapred.output.compress", "true")
//  conf.setMapOutputCompressorClass(c)
//  conf.set("mapred.output.compression.codec", c.getCanonicalName)
//  conf.set("mapred.output.compression.type", CompressionType.BLOCK.toString)

//System.setProperty("spark.hadoop.mapred.output.compress", "true")
//System.setProperty("spark.hadoop.mapred.output.compression.codec", "true")
//System.setProperty("spark.hadoop.mapred.output.compression.codec", "org.apache.hadoop.io.compress.GzipCodec")
//System.setProperty("spark.hadoop.mapred.output.compression.type", "BLOCK")