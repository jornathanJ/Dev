package com.bistel.edu

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Test1 extends App {

  def addOne(m: Int): Int = {
    m + 1;
  }
  
  val c = (x: Int) => {
    x + 1;
  }
  
  val conf = new SparkConf().setAppName("TransformFilter").setMaster("local[*]")
  val sc = new SparkContext(conf)
  
  val rdd = sc.parallelize(1 to 5)
  val a = rdd.filter(_ %2!=0).collect
  //a.foreach(println)
  
  val b = addOne(1);
  println(b);
  
  println(c(5));
  
  val vv = bigger("vvv");
  println(vv);
    
  val times = 1
  val ii =  times match {
    case i if i == 1 => "one"
    case i if i == 2 => "two"
    case _ => "some other number"
  }
  
  println(ii);
  
  //List(1, 2, 3) map squared
  var numbers = List(1,2,3,4,5,6,7,8,9)
  
  def timesTwo(i: Int): Int = {
    i * 2
  }
  //numbers.foreach((i: Int) => i * 2)
  val vvv = numbers.find((i: Int) => i > 50)
  numbers.foreach(println);  
  println(vvv);
  
  val nestedNumbers = List(List(1, 2), List(3, 4))
  val na = nestedNumbers.map((x: List[Int]) => x.map(_ * 2)).flatten
  println(na);
  
  def bigger(o: Any): Any = {
    o match {
      case i: Int if i < 0 => i - 1
      case i: Int => i + 1
      case d: Double if d < 0.0 => d - 0.1
      case d: Double => d + 0.1
      case text: String => text + "s"
    }
    
    
  
  
}
  
  
  
    
}