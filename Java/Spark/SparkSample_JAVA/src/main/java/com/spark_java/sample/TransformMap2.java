package com.spark_java.sample;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class TransformMap2 {

	public static void main(String[] args) {

		SparkConf conf = new SparkConf().setAppName("TransformMap").setMaster(
				"local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);

		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
		JavaRDD<Integer> rdd = sc.parallelize(data);

		JavaRDD<Integer> a = rdd.map(new Function<Integer, Integer>() {
			public Integer call(Integer s) {
				return s * 2;
			}
		});

		List<Integer> output = a.collect();

		for (Integer tuple : output) {
			System.out.println(tuple);
		}

		JavaPairRDD<String, Integer> b = rdd
				.mapToPair(new PairFunction<Integer, String, Integer>() {
					@Override
					public Tuple2<String, Integer> call(Integer s) {
						return new Tuple2<String, Integer>(s.toString(), s * s
								* s);
					}
				});
		
		List<Tuple2<String, Integer>> output2 = b.collect();

		for (Tuple2<?, ?> tuple : output2) {
			System.out.println(tuple._1() + ": " + tuple._2());
		}
		
//		JavaRDD<Integer> c = rdd.filter(new Function<Integer, Boolean>() {
//			 public Boolean call(Integer s) { return s%2!=0  ; }
//		});
		
		JavaRDD c = rdd.filter(new Function<Integer, Boolean>() {
			 public Boolean call(Integer s) { return s%2!=0  ; }
		});
		
		long num = c.count();
		
		System.out.println(num);
		
		sc.stop();
		sc.close();

	}

}
