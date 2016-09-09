package com.spark_java.sample;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import scala.Tuple2;

public class JTransformGroupBy {
	public static void main(String args[])
	{
		SparkConf conf = new SparkConf().setAppName("JTransformGroupBy").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);

		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		JavaRDD<Integer> x = sc.parallelize(data);
		
		List<Tuple2<String, Iterable<Integer>>> y = x.groupBy(new Function<Integer, String>()
		{
			@Override
			public String call(Integer i)
			{
				if (i % 2 == 0)
				{
					return "even";
				}
				else
				{
					return "add";
				}
			}
		}).collect();
		
		System.out.println("Y=================");
		for (int i = 0; i < y.size(); i++)
		{
			System.out.println(y.get(i));
		}
		
		sc.stop();
		sc.close();
	}
}
