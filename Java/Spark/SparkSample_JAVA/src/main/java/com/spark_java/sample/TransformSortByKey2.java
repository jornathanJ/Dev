package com.spark_java.sample;

import java.util.Arrays;
import java.util.List;
//import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.Function;
//import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class TransformSortByKey2 {

	public static void main(String[] args) {

		SparkConf conf = new SparkConf().setAppName("TransformMap").setMaster(
				"local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);

		List<String> data = Arrays.asList("dog", "cat", "owl", "gnu", "ant");
		JavaRDD<String> a = sc.parallelize(data, 2);
		
		 
		 
		
		List<Integer> ydata = Arrays.asList(1, 2, 3, 4, 5);
		JavaRDD<Integer> b = sc.parallelize(ydata,2);
		
		JavaPairRDD<String, Integer> c = a.zip(b);
		
		JavaPairRDD<String, Integer> x = c.sortByKey(true);
		
		JavaPairRDD<String, Integer> y = c.sortByKey(false);
		
		 
		List<Tuple2<String, Integer>> output2 = x.collect();

		for (Tuple2<?, ?> tuple : output2) {
			System.out.println(tuple._1() + ": " + tuple._2());
		}
		 
		
		List<Tuple2<String, Integer>> output3 = y.collect();
		
		for ( Tuple2<?,?> tuple2 : output3){
			System.out.println(tuple2._1() + ": " + tuple2._2());
		}
		 
		 
		sc.stop();
		sc.close();

	}

}
