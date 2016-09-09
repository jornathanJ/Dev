package com.spark_java.sample;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.apache.spark.api.java.function.DoubleFlatMapFunction;
//import org.apache.spark.api.java.function.DoubleFunction;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.VoidFunction;

public class Transform_FlatMap {

	public static void main(String[] args) throws Exception {
		
	
	SparkConf sparkConf = new SparkConf().setAppName("Transform_FlatMap").setMaster("local[*]");
	JavaSparkContext sc = new JavaSparkContext(sparkConf);

	JavaRDD<Integer> rdd = sc.parallelize(Arrays.asList(1, 2, 3));
	

	//-------------------------------------------------
	//
	//SCALA CODE : val a = rdd.flatMap(1 to _).collect
	//
	//-------------------------------------------------
	JavaRDD<Double> flatMap = rdd.flatMap(new FlatMapFunction<Integer, Double>() {
		@Override
	      public Iterable<Double> call(Integer s) {
	        List<Double> lengths = new LinkedList<Double>();
	        for (int num = 1 ; num <= s; num++){ 
	        	lengths.add(num * 1.0);
	        }
	        return lengths;
	      }
	    }
	);
	
	flatMap.collect();
	System.out.println("flatMap----------------------------------");
	flatMap.foreach(s -> System.out.println(s));
	
	//--------------------------------------------------------------------flatMapToDouble을 사용하는 경우
	JavaDoubleRDD doubles = rdd.flatMapToDouble(new DoubleFlatMapFunction<Integer>() {
	      @Override
	      public Iterable<Double> call(Integer s) {
	        List<Double> lengths = new LinkedList<Double>();
	        for (int num = 1 ; num <= s; num++){ 
	        	lengths.add(num * 1.0);
	        }
	        return lengths;
	      }
	    });
	doubles.collect();
	System.out.println("flatMapToDouble----------------------------------");
	doubles.foreach(s -> System.out.println(s));
	
	
	//	JavaRDD<Double> Map = rdd.map(
	//			new Function<Integer, Double>() {
	//		@Override
	//	      public Double call(Integer s) {
	//	        return s*1.0;
	//	      }
	//	    }
	//	);

	
	//-------------------------------------------------
	//
	//SCALA CODE : val b = rdd.map(x => x).collect
	//
	//-------------------------------------------------
	JavaRDD<Double> Map2 = rdd.map(s -> s*1.0);
	
	System.out.println("Map2----------------------------------");
	Map2.collect();
	Map2.foreach(s -> System.out.println(s));
	
	//-------------------------------------------------
	//
	//SCALA CODE : val c = rdd.map(x => List(x)).collect
	//
	//-------------------------------------------------
	JavaRDD<Object> Map3 = rdd.map(new Function<Integer, Object>() {
		@Override
		public Object call(Integer s) {
			List<Double> lengths = new LinkedList<Double>();
	        for (int num = 1 ; num <= s; num++){ 
	        	lengths.add(num * 1.0);
	        }
			return lengths;
		}
	});
	
	System.out.println("Map3----------------------------------");
	Map3.collect();
	Map3.foreach(s -> System.out.println(s));
	
	//-------------------------------------------------
	//
	//SCALA CODE : val d = rdd.flatMap(x => List(x)).collect
	//
	//-------------------------------------------------
	JavaRDD<Object> Map4 = rdd.flatMap(new FlatMapFunction<Integer, Object>() {
		@Override
		public Iterable<Object> call(Integer s) {
			List<Object> lengths = new LinkedList<Object>();
	        for (int num = 1 ; num <= s; num++){ 
	        	lengths.add(num * 1.0);
	        }
			return lengths;
		}
	});
	
	System.out.println("Map4----------------------------------");
	Map4.collect();
	Map4.foreach(s -> System.out.println(s));
	
	
	sc.stop();		
	sc.close();
	}
}


//--------------------------------------------------------------------mapToDouble을 사용하는 경우
//JavaRDD<Integer> rdd2 = sc.parallelize(Arrays.asList(1, 2, 3, 4, 5));
//
//JavaDoubleRDD doubles2 = rdd.mapToDouble(
//		new DoubleFunction<Integer>() {
//			  public double call(Integer s) {
//		        //foreachCalls++;
//		        return 1.0 ;
//		      }
//		}
//);
//doubles2.collect();