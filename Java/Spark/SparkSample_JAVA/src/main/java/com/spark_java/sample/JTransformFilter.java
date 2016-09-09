package com.spark_java.sample;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

public class JTransformFilter {
	public static void main(String args[])
	{
		SparkConf conf = new SparkConf().setAppName("JTransformFilter").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);

		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
		JavaRDD<Integer> rdd = sc.parallelize(data);
		List<Integer> a = rdd.filter(new Function<Integer, Boolean>(){
			public Boolean call(Integer i) { return i % 2 != 0; }
		}).collect();
		
		for (int i = 0; i < a.size(); i++)
		{
			System.out.println(a.get(i));
		}
		
//		JavaRDD<Integer> r = rdd.filter(x-> x%2 != 0);
//		r.collect();
//		
//		r.foreach(s -> System.out.println(s));
		
		sc.stop();
		sc.close();
	}
}
