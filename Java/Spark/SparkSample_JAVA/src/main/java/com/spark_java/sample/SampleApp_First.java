package com.spark_java.sample;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.VoidFunction;

import java.util.Arrays;

public final class SampleApp_First {

	//static int foreachCalls = 0;
	
	public static void main(String[] args) throws Exception {

		SparkConf sparkConf = new SparkConf().setAppName("JavaWordCount").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(sparkConf);

		JavaRDD<String> readme = sc.textFile("E:\\200.Spark\\spark-1.5.0-bin-hadoop2.6\\README.md");
		JavaRDD<String> lineWithSpark = readme.filter(line -> line.contains("Spark"));
		
		//flatMap --> String
		JavaRDD<String> words = lineWithSpark.flatMap(filteredlineData -> Arrays.asList(filteredlineData.split(" ")[1]));
		
		//map --> Object
		//JavaRDD<Object> words = lineWithSpark.map(line -> Arrays.asList(line.split(" ")[1]));

		//lineWithSpark.foreach()
		
		/*
		lineWithSpark.foreach(new VoidFunction<String>() {
		      @Override
		      public void call(String s) {
		        foreachCalls++;
		        System.out.println(s);
		      }
		    });
		
		
		# Apache Spark
	    MASTER=spark://host:7077 ./bin/run-example SparkPi
		Spark is a fast and general cluster computing system for Big Data. It provides
		Testing first requires [building Spark](#building-spark). Once Spark is built, tests
		rich set of higher-level tools including Spark SQL for SQL and DataFrames,
		and Spark Streaming for stream processing.
		Spark uses the Hadoop core library to talk to HDFS and other Hadoop-supported
		Hadoop, you must build Spark against the same version that your cluster runs.
		You can find the latest Spark documentation, including a programming
		## Building Spark
		for guidance on building a Spark application that works with a particular
		Spark is built using [Apache Maven](http://maven.apache.org/).
		To build Spark and its example programs, run:
		in the online documentation for an overview on how to configure Spark.
		["Building Spark"](http://spark.apache.org/docs/latest/building-spark.html).
		The easiest way to start using Spark is through the Scala shell:
		Spark also comes with several sample programs in the `examples` directory.
		    ./bin/run-example SparkPi
	    */
		
		
		//		words.foreach(new VoidFunction<String>() {
		//		      @Override
		//		      public void call(String s) {
		//		        //foreachCalls++;
		//		        System.out.println(s);
		//		      }
		//		    });
		
		words.foreach(s -> System.out.println(s));
		
		
		
		//map --> Object
		//			words.foreach(new VoidFunction<Object>() {
		//			      @Override
		//			      public void call(Object s) {
		//			        foreachCalls++;
		//			        System.out.println(s.toString());
		//			      }
		//			    });
		
		long apacheCnt = words.filter(f -> f.contains("a")).count();
		long isCnt = words.filter(f -> f.contains("b")).count();
		
		System.out.printf("Line with a : %d, Line with b : %d \r\n", apacheCnt, isCnt);
		
		sc.stop();		
		sc.close();
	}
}
