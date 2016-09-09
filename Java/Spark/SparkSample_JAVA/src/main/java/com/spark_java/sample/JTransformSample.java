package com.spark_java.sample;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class JTransformSample {
	public static void main(String args[])
	{
		SparkConf conf = new SparkConf().setAppName("JTransformFilter").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);

		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		JavaRDD<Integer> rdd = sc.parallelize(data);
		List<Integer> a = rdd.sample(false, 0.5, 1).collect();
		List<Integer> b = rdd.sample(true, 0.5, 1).collect();
		
		System.out.println("A==========");
		for (int i = 0; i < a.size(); i++)
		{
			System.out.println(a.get(i));
		}
		
		System.out.println("B==========");
		for (int i = 0; i < b.size(); i++)
		{
			System.out.println(b.get(i));
		}
		
		sc.stop();
		sc.close();
	}
}
