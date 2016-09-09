package com.spark_java.sample;


import java.io.Serializable;
import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;
//import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
//import org.sparkexample.Transform_MapPartition.AvgCount;

import scala.Tuple2;



public class Transform_SortBy {
	
//	private static final Pattern SPACE = Pattern.compile(" ");
	
	static class temp implements Serializable{

		private static final long serialVersionUID = 1L;
		public temp() {
	      arg01 = "";
	      arg02 = 0;
	    }
	    public temp(String arg01, Integer arg02) {
	    	this.arg01 = arg01;
	    	this.arg02 = arg02;
	    }
	    
	    public String arg01;
	    public Integer arg02;
	   
	    public String getArg01(){
	    	return arg01;
	    }
	    
	    public Integer getArg02(){
	    	return arg02;
	    }
	  }
	
	
	
	public static void main(String[] args) throws Exception {
		
		JavaSparkContext ctx = new JavaSparkContext(new SparkConf().setAppName("Transform_Cartesian").setMaster("local[*]"));
		
		
		//List<Integer> data = Arrays.asList(("H",10),("k",26),("m",1),("u",8),("s",5));//val rdd = sc.parallelize(Array(("H",10),("k",26),("m",1),("u",8),("s",5)))
		List<Tuple2<String, Integer>> pairs = new ArrayList<>();
	    pairs.add(new Tuple2<>("H", 10));
	    pairs.add(new Tuple2<>("k", 26));
	    pairs.add(new Tuple2<>("m", 1));
	    pairs.add(new Tuple2<>("u", 8));
	    pairs.add(new Tuple2<>("s", 5));
        
		JavaRDD<Tuple2<String, Integer>> rdd = ctx.parallelize(pairs);
		
		JavaRDD<Tuple2<String, Integer>> sortedRDD = rdd.sortBy(new Function<Tuple2<String, Integer>, String>() {
		      @Override
		      public String call(Tuple2<String, Integer> t) {
		        return t._1;
		      }
		    }, true, 2);
		
		//sortedRDD.collect();
		List<Tuple2<String, Integer>> z = sortedRDD.collect();
		System.out.println("z===========");
		for (Tuple2<?,?> tuple : z) {
		      System.out.println(tuple._1() + ", " + tuple._2());
		    }
		
		
		sortedRDD = rdd.sortBy(new Function<Tuple2<String, Integer>, Integer>() {
		      @Override
		      public Integer call(Tuple2<String, Integer> t) {
		        return t._2;
		      }
		    }, true, 2);
		
		//sortedRDD.collect();
		List<Tuple2<String, Integer>> zz = sortedRDD.collect();
		System.out.println("z===========");
		for (Tuple2<?,?> tuple : zz) {
		      System.out.println(tuple._1() + ", " + tuple._2());
		    }
	    
		ctx.stop();
		ctx.close();				 
	   
	}
	  
}