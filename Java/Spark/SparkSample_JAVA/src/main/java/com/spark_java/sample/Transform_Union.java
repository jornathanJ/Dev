package com.spark_java.sample;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Transform_Union {

	public static void main(String[] args) throws Exception {
		
		
		SparkConf sparkConf = new SparkConf().setAppName("Transform_FlatMap").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(sparkConf);

		JavaRDD<Integer> rdd = sc.parallelize(Arrays.asList(1, 2, 3, 4, 5, 6));
		JavaRDD<Integer> a = rdd.sample(false,  0.5, 1);
		JavaRDD<Integer> b = rdd.sample(true,  0.5, 1);
		List<Integer> c = a.union(b).collect();
		
		rdd.foreach(s -> System.out.println(s));
		
		System.out.println("Result of A ----------------------------------");
		a.foreach(s -> System.out.println(s));
		
		System.out.println("Result of B ----------------------------------");
		b.foreach(s -> System.out.println(s));
		
		System.out.println("Result of Union ----------------------------------");
		c.forEach(s -> System.out.println(s));
		
		sc.stop();		
		sc.close();
		
	}
}
