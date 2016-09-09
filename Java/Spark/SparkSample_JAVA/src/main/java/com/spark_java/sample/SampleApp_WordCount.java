package com.spark_java.sample;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
//import org.apache.spark.api.java.function.Function;
//import org.apache.spark.sql.DataFrame;
//import org.apache.spark.sql.SQLContext;
//import org.apache.spark.storage.StorageLevel;

//import bean.table01;

public class SampleApp_WordCount {
	
	private static final Pattern SPACE = Pattern.compile(" ");

	public static void main(String[] args) throws Exception {
		
		JavaSparkContext ctx = new JavaSparkContext(new SparkConf().setAppName("WordCount").setMaster("local[*]"));
	    
	    //HiveContext
	    JavaRDD<String> lines = ctx.textFile("C:/spark-1.5.0-bin-hadoop2.6/README.md", 1);
	    
	    JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
		      
		      @Override
		      public Iterable<String> call(String s) {
		        return Arrays.asList(SPACE.split(s));
		      }
		    });
	    
	    ctx.stop();
	    ctx.close();
	}
	  
}