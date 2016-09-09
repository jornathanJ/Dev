package com.spark_java.sample;


import java.util.Arrays;
import java.util.List;
//import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;


public class Transform_Cartesian {
	
	//private static final Pattern SPACE = Pattern.compile(" ");
	
	public static void main(String[] args) throws Exception {
		
		JavaSparkContext ctx = new JavaSparkContext(new SparkConf().setAppName("Transform_Cartesian").setMaster("local[*]"));
		
		List<Integer> data = Arrays.asList(1,2,3);
		JavaRDD<Integer> x = ctx.parallelize(data);
		
		List<Integer> dataa = Arrays.asList(8,9);
		JavaRDD<Integer> y = ctx.parallelize(dataa);

		List<Tuple2<Integer, Integer>> z = x.cartesian(y).collect();
		System.out.println("z===========");
		for (Tuple2<?,?> tuple : z) {
		      System.out.println(tuple._1() + ", " + tuple._2());
		    }
	    
		ctx.stop();
		ctx.close();				 
	   
	}
	  
}