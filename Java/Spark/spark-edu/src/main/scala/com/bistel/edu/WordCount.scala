

package com.bistel.edu


import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext._;
import org.apache.spark.SparkConf;

object WordCount {
   def main(args : Array[String]){
     val conf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
     val sc = new SparkContext(conf)
     val file = sc.textFile(args(0))
     val word = file.flatMap(_.split(" ")).map(w => (w, 1)).cache()
     word.reduceByKey(_ + _).saveAsTextFile(args(1))
     
   }
}

