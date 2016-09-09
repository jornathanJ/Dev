package com.raonbit.edu

object function extends App {
  
  def fact(x:Int):Int = if(x==0) 1 else x + fact(x-1)
  
  def cube(x:Int):Int = x*x*x
  
  def sumFactorial(a:Int, b:Int):Int ={
    if(a>b) 0 else
      fact(a) + sumFactorial(a+1, b)
  }
  
  def sumCube(a:Int, b:Int):Int = if(a>b) 0 else cube(a) + sumCube(a+1, b)
  
  def sum(f:Int => Int, a:Int, b:Int):Int = if(a>b) 0 else cube(a) + sum(f, a+1, b)
  
  def sumFactorial2(a:Int, b:Int):Int = sum(fact, a, b)
  
  def sumCube2(a:Int, b:Int):Int = sum(cube, a, b)
  
  //Anonymous Function
  (x:Int) => x*x*x
  (x:Int, y:Int) => x + y
  
  def sumCube3(a:Int, b:Int):Int =
    sum(x => x*x*x, a, b)
    
  //Partilly applied funcion  
  def sumCube4 = sumCube3(2, _:Int)
  
  val what = sumCube4(3)
  println(what)
  
}