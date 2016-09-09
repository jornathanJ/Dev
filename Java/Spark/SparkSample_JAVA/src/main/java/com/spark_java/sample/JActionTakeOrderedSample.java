package com.spark_java.sample;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class JActionTakeOrderedSample {
	public static void main(String args[])
	{
		SparkConf conf = new SparkConf().setAppName("JActionTakeOrderedSample").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		List<String> data = Arrays.asList("dog", "cat", "ape", "salmon", "gnu");
		JavaRDD<String> rdd = sc.parallelize(data, 2);
	
		List<String> x = rdd.takeOrdered(2);
		List<String> y = rdd.takeSample(true, 2, 1);
		
		
		System.out.println("X=================");
		for (int i = 0; i < x.size(); i++)
		{
			System.out.println(x.get(i));
		}
		
		System.out.println("Y=================");
		for (int i = 0; i < y.size(); i++)
		{
			System.out.println(y.get(i));
		}
		
		sc.stop();
		sc.close();
	}	
	
	

}
