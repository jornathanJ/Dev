package com.spark_java.sample;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.Function;
//import org.apache.spark.api.java.function.PairFunction;
//
//import scala.Tuple2;

public class ActionCollectAsMap2 {

	public static void main(String[] args) { 

		SparkConf conf = new SparkConf().setAppName("TransformMap").setMaster(
				"local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);

		List<Integer> data = Arrays.asList(1, 2, 1, 3);
		JavaRDD<Integer> a = sc.parallelize(data, 1);
		
		List<Integer> ydata = Arrays.asList(1, 2, 4, 3);
		JavaRDD<Integer> b = sc.parallelize(ydata,1);
		
		JavaPairRDD<Integer, Integer> c = a.zip(b);
		
		Map<Integer, Integer> mc = c.collectAsMap();

		 
		 
		System.out.println(mc.toString());
		 

		sc.stop();
		sc.close();
	}

}
