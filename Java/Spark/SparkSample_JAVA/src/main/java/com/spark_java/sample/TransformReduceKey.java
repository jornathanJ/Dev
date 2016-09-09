package com.spark_java.sample;

//import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.FlatMapFunction;
//import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
//import org.apache.spark.sql.DataFrame;
//import org.apache.spark.sql.SQLContext;
//import org.apache.spark.storage.StorageLevel;
//
//import scala.Tuple2;
//import bean.table01;

public class TransformReduceKey {
	
//	private static final Pattern SPACE = Pattern.compile(" ");

	public static void main(String[] args) throws Exception {
		
		JavaSparkContext ctx = new JavaSparkContext(new SparkConf().setAppName("TransformReduceKey").setMaster("local[*]"));
		
		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5,6,7,8,9,10);
		JavaRDD<Integer> distData = ctx.parallelize(data, 3);
		//System.out.println(distData.collect());
		for(int a =0; a < distData.collect().size(); a++){
			System.out.println(distData.collect().get(a));
		}
		
		Integer counts = distData.reduce(new Function2<Integer, Integer, Integer>() {
		      @Override
		      public Integer call(Integer i1, Integer i2) {
		    	  System.out.println("i1="+i1+"   "+ i2);
		        return i1 + i2;
		      }
		    });
		System.out.println(counts);
		
		
		Integer counts2 = distData.reduce(new Function2<Integer, Integer, Integer>() {
		      @Override
		      public Integer call(Integer i1, Integer i2) {
		    	  //System.out.println("i1="+i1+"   "+ i2);
		        return i1 - i2;
		      }
		    });
		System.out.println(counts2);
	    //HiveContext
	    //JavaRDD<String> lines = ctx.textFile("C:/spark-1.5.0-bin-hadoop2.6/README.md", 1);
	    //temp.map(f)
		/*JavaRDD<Integer> lineLengths = distData.map(s -> s * 2);
		System.out.println(lineLengths.collect().toString());
		
		JavaRDD<Object> lineLengthsa = distData.map(x -> (x + ",(" +x*x*x+ ")"));
		System.out.println(lineLengthsa.collect().toString());*/
	    //JavaRDD<table01> temp = temps.map(null);
		ctx.stop();
	    ctx.close();
	}
	  
}