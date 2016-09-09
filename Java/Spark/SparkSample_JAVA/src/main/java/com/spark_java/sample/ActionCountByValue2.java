package com.spark_java.sample;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

//import scala.Tuple2;

public class ActionCountByValue2 {

	public static void main(String[] args) { 

		SparkConf conf = new SparkConf().setAppName("TransformMap").setMaster(
				"local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);

		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 2, 4, 2, 1, 1, 1, 1, 1);
		JavaRDD<Integer> rdd = sc.parallelize(data, 3);

		Map<Integer, Long> x = rdd.countByValue();
		
		System.out.println(x.toString());
		 
		
		sc.stop();
		sc.close();
	}

}
