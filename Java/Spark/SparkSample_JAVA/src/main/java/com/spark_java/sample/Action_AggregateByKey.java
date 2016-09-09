package com.spark_java.sample;


import java.util.Arrays;
import java.util.List;
//import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;


public class Action_AggregateByKey {
	
	//private static final Pattern SPACE = Pattern.compile(" ");
	
	public static void main(String[] args) throws Exception {
		
		JavaSparkContext ctx = new JavaSparkContext(new SparkConf().setAppName("Transform_MapPartition")
				.setMaster("local[*]"));
		
		List<String> data = Arrays.asList("tt","rrrr","eee","www","qqq");
		JavaRDD<String> distData = ctx.parallelize(data,2);

		distData.saveAsObjectFile("C:/scala-workspace/edu-test-file");
		ctx.objectFile("C:/scala-workspace/edu-test-file");
		/*rdd.saveAsObjectFile("C:/scala-workspace/edu-test-file")
	    val loadRdd = sc.objectFile("C:/scala-workspace/edu-test-file")*/
		
		ctx.stop();
		ctx.close();				 
	   
	}
	  
}