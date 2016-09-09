package com.spark_java.sample;

//import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.FlatMapFunction;
//import org.apache.spark.api.java.function.Function;
//import org.apache.spark.sql.DataFrame;
//import org.apache.spark.sql.SQLContext;
//import org.apache.spark.storage.StorageLevel;

//import scala.Tuple2;
//import bean.table01;

 
public class TransformMap {
	
//	private static final Pattern SPACE = Pattern.compile(" ");

	public static void main(String[] args) throws Exception {
		
		JavaSparkContext ctx = new JavaSparkContext(new SparkConf().setAppName("WordCount").setMaster("local[*]"));
		
		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
		JavaRDD<Integer> distData = ctx.parallelize(data);
	    //HiveContext
	    //JavaRDD<String> lines = ctx.textFile("C:/spark-1.5.0-bin-hadoop2.6/README.md", 1);
	    //temp.map(f)
		JavaRDD<Integer> lineLengths = distData.map(s -> s * 2);
		System.out.println(lineLengths.collect().toString());
		
		JavaRDD<Object> lineLengthsa = distData.map(x -> (x + ",(" +x*x*x+ ")"));
		System.out.println(lineLengthsa.collect().toString());
	    //JavaRDD<table01> temp = temps.map(null);
	   
		ctx.stop();
	    ctx.close();
	}
	  
}