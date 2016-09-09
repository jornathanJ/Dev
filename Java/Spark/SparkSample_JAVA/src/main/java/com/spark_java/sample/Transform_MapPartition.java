package com.spark_java.sample;


import java.io.Serializable;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
//import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;


public class Transform_MapPartition {
	
//	private static final Pattern SPACE = Pattern.compile(" ");
	
	//static class AvgCount implements Serializable{
	static class AvgCount {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public AvgCount() {
	      total_ = 0;
	      num_ = 0;
	    }
	    public AvgCount(Integer total, Integer num) {
		    total_ = total;
		    num_ = num;
	    }
	    public AvgCount merge(Iterable<Integer> input) {
	      for (Integer elem : input) {
	        num_ += 1;
	        total_ += elem;
	      }
	      return this;
	    }
	    public Integer total_;
	    public Integer num_;
	    public float avg() {
		    return total_ / (float) num_;
	    }
	    
	    public Integer getTotal(){
	    	return total_;
	    }
	    
	    public Integer getNum(){
	    	return num_;
	    }
	  }
	
	public static void main(String[] args) throws Exception {
		
		JavaSparkContext ctx = new JavaSparkContext(new SparkConf().setAppName("Transform_MapPartition").setMaster("local[*]"));
		
		
		
		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24);
		JavaRDD<Integer> distData = ctx.parallelize(data,8);

		JavaRDD<AvgCount> a = distData.mapPartitions(new FlatMapFunction<Iterator<Integer>, AvgCount>() {
            @Override
            public Iterable<AvgCount> call(Iterator<Integer> iter) throws Exception {
            	
            	ArrayList<AvgCount> ret = new ArrayList<AvgCount>();
            	int pre = iter.next();
                while (iter.hasNext()) {
                	AvgCount a = new AvgCount(0, 0);
                    a.total_ = iter.next();
  		            a.num_ = pre;
  		            ret.add(a);
  		            pre = a.getTotal();
  		          
                }
                return ret;
            }
        });
		//VoidFunction<AvgCount> f
		System.out.println(a.collect());
		for(int i =0; i< a.collect().size(); i++){
			System.out.println(a.collect().get(i).getNum()+","+a.collect().get(i).getTotal());
		}
		/* val rdd = sc.parallelize(1 to 24, 8)
		rdd.mapPartitions(myfunc).collect().foreach(println)*/
		
		ctx.stop();
		ctx.close();				 
	   
	}
	  
}