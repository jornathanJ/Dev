package com.spark_java.sample;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import com.google.common.base.Optional;

import scala.Tuple2;

public class JTransformJoin {
	public static void main(String args[])
	{
		SparkConf conf = new SparkConf().setAppName("JTransformJoin").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		List<String> data1 = Arrays.asList("dog", "salmon", "salmon", "rat", "elephant");
		List<String> data2 = Arrays.asList("dog", "cat", "gnu", "salmon", "rabbit", "turkey", "wolf",  "bear", "bee");
		
		JavaRDD<String> a = sc.parallelize(data1, 3);
		JavaPairRDD<Integer, String> b =  a.keyBy(new Function<String, Integer>(){
			@Override
			public Integer call(String s)
			{
				return s.length();
			}
		});
		
		JavaRDD<String> c = sc.parallelize(data2, 3);
		JavaPairRDD<Integer, String> d =  c.keyBy(new Function<String, Integer>(){
			@Override
			public Integer call(String s)
			{
				return s.length();
			}
		});
		
		List<Tuple2<Integer, Tuple2<String, String>>> w = b.join(d).collect();
		List<Tuple2<Integer,Tuple2<String,Optional<String>>>> x = b.leftOuterJoin(d).collect();
		List<Tuple2<Integer,Tuple2<Optional<String>,String>>> y = b.rightOuterJoin(d).collect();
		List<Tuple2<Integer, Tuple2<Optional<String>, Optional<String>>>> z = b.fullOuterJoin(d).collect();
		
		
		System.out.println("W=================");
		System.out.println(w);

		System.out.println("X=================");
		for (int i = 0; i < x.size(); i++)
		{
			System.out.println(x.get(i));
		}
		
		System.out.println("Y=================");
		for (int i = 0; i < y.size(); i++)
		{
			System.out.println(y.get(i));
		}
		
		System.out.println("Z=================");
		for (int i = 0; i < z.size(); i++)
		{
			System.out.println(z.get(i));
		}
		
		sc.stop();
		sc.close();
	}
}
