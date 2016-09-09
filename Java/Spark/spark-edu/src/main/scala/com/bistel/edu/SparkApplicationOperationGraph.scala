package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;
import org.apache.spark.TaskContext;

object SparkApplicationOperationGraph extends App {
 
  val conf = new SparkConf().setAppName("Fist").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val format = new java.text.SimpleDateFormat("yyyy-MM-dd")
  case class Register (d: java.util.Date, uuid :String,  cust_id : String, lat : Float, lng : Float)
  case class Click ( d: java.util.Date, uuid : String, cust_id : String, landing_page : Int)
  
  
  //val reg = sc.textFile("E:\\200.Spark\\SparkWorkSpace\\spark-edu\\spark_lab_files\\reg.tsv").map(_split("\t")).map(r => (r(1), Register
  
}