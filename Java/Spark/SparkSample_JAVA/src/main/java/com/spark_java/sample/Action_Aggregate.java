package com.spark_java.sample;

import java.util.Arrays;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

//import scala.Serializable;

public class Action_Aggregate {
	
//	public static class AvgCount implements Serializable {
//	    public AvgCount(int total, int num) {
//		    total_ = total;
//		    num_ = num;
//	    }
//	    public int total_;
//	    public int num_;
//	    public float avg() {
//		    return total_ / (float) num_;
//	    }
//	  }

	public static void main(String[] args) throws Exception {
		
	
	SparkConf sparkConf = new SparkConf().setAppName("Action_Aggregate")
			.setMaster("spark://192.168.8.112:7077");
			//.setSparkHome("/usr/local/spark");
	//SparkConf sparkConf = new SparkConf().setAppName("Action_Aggregate").setMaster("local[*]");
	JavaSparkContext sc = new JavaSparkContext(sparkConf);
			
	JavaRDD<Integer> rdd = sc.parallelize(Arrays.asList(1, 2, 3, 4), 2);
	
	
	Function2<Integer, Integer, Integer> getMaxValue = new Function2<Integer, Integer, Integer>() {
	    @Override
	    public Integer call(Integer a, Integer b) {
        return Math.max(a, b);
	    }
    };
    Function2<Integer, Integer, Integer> addData = new Function2<Integer, Integer, Integer>() {
	    @Override
	    public Integer call(Integer a, Integer b) {
      
        return a+b;
	    }
    };
    
    //-------------------------------------------------
  	//
  	//SCALA CODE : val a = rdd1.aggregate(0)(math.max(_,_), _+_)  
  	//
  	//-------------------------------------------------
    //���ٽ��� ���ٸ� ��� �Դϴ�.
	//Integer a = rdd.aggregate(0, getMaxValue, addData);
//    Integer a = rdd.aggregate(0
//    		, (first,next) -> Math.max(first, next)
//    		, (first,next) -> first + next);
//    
//    printNum("A", a);
	
	//-------------------------------------------------
	//
	//SCALA CODE : val f = rdd4.aggregate("")((x, y) => (math.max(x.length,y.length).toString), (x, y) => x+ y)  
	//
	//-------------------------------------------------
//	JavaPairRDD<Object, Integer> y = rdd.keyBy(in -> in % 2); 
//	
//	JavaRDD<String> rdd4 = sc.parallelize(Arrays.asList("12", "23", "345", ""), 4);
//	String f = rdd4.aggregate(""
//			, (first, next) -> Integer.toString(Math.max(first.length(), next.length()))
//			, (first, next) -> first + next);
	
	//printNumString("F", f);
	
	//Sleep(1000);
	
	sc.stop();		
	sc.close();
	}
	
	public static void printNum(String sKey, Integer oValue){
		System.out.println(sKey);
		System.out.println("======================");
		System.out.println(oValue.toString());
	}
	
	public static void printNumString(String sKey, String oValue){
		System.out.println(sKey);
		System.out.println("======================");
		System.out.println(oValue);
	}
}

