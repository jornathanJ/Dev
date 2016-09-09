package com.spark_java.sample;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
//import org.apache.spark.api.java.function.PairFunction;

//import scala.Tuple2;

public class ActionReduce2 {

	public static void main(String[] args) { 

		SparkConf conf = new SparkConf().setAppName("TransformMap").setMaster(
				"local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);

		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		JavaRDD<Integer> rdd = sc.parallelize(data, 3);

		long x = rdd.reduce(new Function2<Integer, Integer, Integer>() {

			@Override
			public Integer call(Integer v1, Integer v2) throws Exception {
				// TODO Auto-generated method stub
				return v1 + v2;
			}
		});

		System.out.println(x);

		long y = rdd.reduce(new Function2<Integer, Integer, Integer>() {

			@Override
			public Integer call(Integer v1, Integer v2) throws Exception {
				System.out.println("v1:" + v1 + "v2:"+ v2);
				// TODO Auto-generated method stub
				return v1 - v2;
			}
		});

		System.out.println(y);


		sc.stop();
		sc.close();
	}

}
