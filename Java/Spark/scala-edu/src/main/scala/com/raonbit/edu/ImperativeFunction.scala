package com.raonbit.edu

import java.util.HashMap



object ImperativeFunction extends App {
  
  def printArgs(args : Array[String]) = {
  
    var i =0
    while(i < args.length){
      println(args(i))
      i += 1
    }
  }
  
  def printArgs2(args : Array[String]) = {
    args.foreach(println)
  }
  
  //HashMap h = new HashMap(); 
  
  val hello = Array("Hello", "How", "Area", "you",  "?")
  
  printArgs(hello)
  printArgs2(hello)
  
  
  
  def gcd(x : Long, y : Long) : Long = {
    var a = x
    var b = y
    while(b != 0){
    
      val temp = b
      b = a % b
      a = temp
    }
    a
  }
  
  def gcd2(x: Long, y: Long) : Long = 
    if(y == 0 ) x else gcd(y, x %y)
    
   println(gcd(430, 25))
   println(gcd2(430, 25))
   
}