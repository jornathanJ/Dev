package com.spark_java.sample;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;


public class JParquet {
	public static void main(String args[])
	{
		SparkConf conf = new SparkConf().setAppName("JParquet").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);

		SQLContext sqlContext = new SQLContext(sc);
		DataFrame jsonDF = sqlContext.load("people.par", "parquet");
		jsonDF.show();
	}
}
