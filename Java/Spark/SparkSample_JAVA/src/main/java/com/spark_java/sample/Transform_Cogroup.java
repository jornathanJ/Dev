package com.spark_java.sample;

import java.util.Arrays;
//import java.util.List;

import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;
import scala.Tuple3;
import scala.Tuple4;

public class Transform_Cogroup {

	public static void main(String[] args) throws Exception {
		
		
		SparkConf sparkConf = new SparkConf().setAppName("Transform_Cogroup").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(sparkConf);

		JavaRDD<Integer> rdd = sc.parallelize(Arrays.asList(1, 2, 1, 3), 1);
		
		
	    JavaPairRDD<Integer, String> b = rdd.mapToPair(f -> new Tuple2<>(f, "b"));
	    JavaPairRDD<Integer, String> c = rdd.mapToPair(f -> new Tuple2<>(f, "c"));
	    
	    
	    
	    //-------------------------------------------------
		//
		//SCALA CODE : val x = b.cogroup(c).collect  
		//
		//-------------------------------------------------
	    JavaPairRDD<Integer, Tuple2<Iterable<String>, Iterable<String>>> x = b.cogroup(c);
	    x.collect();
	    
	    JavaPairRDD<Integer, String> d = rdd.mapToPair(f -> new Tuple2<>(f, "d"));
	    JavaPairRDD<Integer, String> e = rdd.mapToPair(f -> new Tuple2<>(f, "e"));
	    JavaPairRDD<Integer,Tuple3<Iterable<String>,Iterable<String>,Iterable<String>>> y = b.groupWith(c, d);
	    JavaPairRDD<Integer,Tuple4<Iterable<String>,Iterable<String>,Iterable<String>,Iterable<String>>> z = b.groupWith(c, d, e);
	    y.collect();
	    
	    
	    
	    System.out.println("Result of cogroup ----------------------------------");
	    x.foreach(s -> System.out.println(s));
	    
	    System.out.println("Result of groupWith ----------------------------------");
	    y.foreach(s -> System.out.println(s));
	    
	    System.out.println("Result of groupWith = Z ----------------------------------");
	    z.foreach(s -> System.out.println(s));
	    
	    
	    
	    //strings.foreach(s -> System.out.println(s));
		
		//JavaPairRDD<Integer, String> readRDD = rdd.mapToPair(in -> (in, "b"));
//		JavaPairRDD<Integer, String > wordOnePairs = rdd.mapToPair(
//                new PairFunction<Integer, Integer, String >() {
//                    public Tuple4<Integer, String> call(Integer x) {
//                        return new Tuple2(x, "b");
//                    }
//                }
//        );
		//JavaRDD<Integer> b = a.map(Object::Integer).cache();
//		  val c = a.map((_, "c"))  
//		  val x = b.cogroup(c).collect
//		    
//		  val d = a.map((_, "d"))
//		  val y = b.groupWith(c, d).collect
//		
//		System.out.println("Result of A ----------------------------------");
//		a.foreach(s -> System.out.println(s));
//		
//		System.out.println("Result of B ----------------------------------");
//		b.foreach(s -> System.out.println(s));
//		
//		System.out.println("Result of Union ----------------------------------");
//		c.forEach(s -> System.out.println(s));
		
		sc.stop();		
		sc.close();
		
	}
}
