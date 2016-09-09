package com.raonbit.edu

object first {
  def square(x:Double) = {
    println("squareCBV is called")
    x*x
  }
  
  def sumOfSquareCBV(x:Double, y:Double):Double ={
    println("sumOfSquareCBV is called")
    square(x) + square(y)
  }
  
  def sumOfSquareCBN(x: => Double, y: => Double):Double ={
    println("sumOfSquareCBN is called")
    square(x) + square(y)
  }
  
  def onTime(time : Long){
    if(time != time) println("onTime")
  }
  
  def onRealTime(time : => Long){
    if(time != time) println("onRealTime")
  }
  
  def main(args : Array[String]) : Unit = {
    println("calling by value")
    var one:Double = sumOfSquareCBV(square(2), square(3))
    //println(one);
    println("calling by name")
    var two = sumOfSquareCBN(square(2), square(3))
    
    var three = onTime(System.nanoTime())
    var four = onRealTime(System.nanoTime())
    
  }
}