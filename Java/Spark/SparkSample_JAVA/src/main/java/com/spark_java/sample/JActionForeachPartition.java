package com.spark_java.sample;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;

public class JActionForeachPartition {
	public static void main(String args[])
	{
		SparkConf conf = new SparkConf().setAppName("JActionForeachPartition").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		List<Integer> data = Arrays.asList(1,2,3,4,5,6,7,8,2,4,2,1,1,1,1,1);
		JavaRDD<Integer> rdd = sc.parallelize(data, 3);
		
		rdd.foreachPartition(new VoidFunction<Iterator<Integer>>() {
			@Override
			public void call(Iterator<Integer> x) {
				System.out.println(x.next() + x.next());
			}
		});
		
		sc.stop();
		sc.close();
	}
}
