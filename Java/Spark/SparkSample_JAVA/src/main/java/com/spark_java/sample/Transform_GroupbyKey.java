package com.spark_java.sample;

import java.util.Arrays;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Transform_GroupbyKey {

	public static void main(String[] args) throws Exception {
		
	
	SparkConf sparkConf = new SparkConf().setAppName("Transform_GroupbyKey").setMaster("local[*]");
	JavaSparkContext sc = new JavaSparkContext(sparkConf);
			
	JavaRDD<Integer> rdd = sc.parallelize(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
	
	
	//-------------------------------------------------
	//
	//SCALA CODE : val y = x.keyBy(_% 2)  
	//
	//-------------------------------------------------
	JavaPairRDD<Object, Integer> y = rdd.keyBy(in -> in % 2); 
	
	//-------------------------------------------------
	//
	//SCALA CODE : val z = x.groupBy(_% 2).collect
	//
	//-------------------------------------------------
	JavaPairRDD<Integer, Iterable<Integer>> z = rdd.groupBy(in -> in %2); 
	
	System.out.println("Result of Y ----------------------------------");
	y.foreach(s -> System.out.println(s));
	
	System.out.println("Result of Z ----------------------------------");
	z.foreach(s -> System.out.println(s));
	
	
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