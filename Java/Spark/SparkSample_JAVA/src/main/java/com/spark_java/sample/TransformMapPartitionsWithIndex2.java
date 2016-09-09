package com.spark_java.sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;

public class TransformMapPartitionsWithIndex2 {

	public static void main(String[] args) {

		SparkConf conf = new SparkConf().setAppName("TransformMap").setMaster(
				"local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);

		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
		JavaRDD<Integer> rdd = sc.parallelize(data, 3);

		JavaRDD<String> r = rdd.mapPartitionsWithIndex(
				new Function2<Integer, Iterator<Integer>, Iterator<String>>() {

					@Override
					public Iterator<String> call(Integer ind,
							Iterator<Integer> iterator) throws Exception {
						ArrayList<String> as = new ArrayList<>();

						while (iterator.hasNext()) {
							String obj = String.valueOf(iterator.next());
							as.add(ind + "," + obj);
							// System.out.println(obj);
						}

						return as.iterator();

					}
				}, false);

		List<String> lr = r.collect();

		for (String s : lr) {
			System.out.println(s);

		}

		sc.stop();
		sc.close();
	}

}
