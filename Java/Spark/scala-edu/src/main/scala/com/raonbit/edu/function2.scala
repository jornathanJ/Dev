package com.raonbit.edu

//import scala.reflect.internal.Kinds$Kind.Head

object function2 extends App {
  
  def sum(f:Int => Int):(Int, Int) => Int = {
    def sumF(a:Int, b:Int):Int = if(a>b) 0 else f(a) + sumF(a+1, b)
    sumF
  }
  
  def sumCube = sum(x => x*x*x)
  println(sumCube(2, 3))
  println(sum(x => x*x*x)(2,3))
  
  def sum2(f:Int => Int)(a:Int, b:Int) :Int = if(a>b) 0 else f(a) + sum2(f)(a+1, b)
  val sumCube2 = sum2(x=>x*x*x) _
  println(sumCube2(2, 3))
  
  def filter(xs:List[Int], f:Int => Boolean):List[Int] = {
    if(xs.isEmpty) xs
    else if(f(xs.head)) xs.head::filter(xs.tail, f)
    else filter(xs.tail, f)
  }
  
  def modN(n:Int)(x:Int) = ((x%n) ==0)
  val nums = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    
  println(filter(nums, modN(2)))
  println(filter(nums, modN(3)))
  
  def toUpper(args:String*) = {
    args.map(arg => arg.toUpperCase)
  }
  
  println(toUpper("dfsdfsdfsdf", "sdfsdfsdf"))
  
  
  
//  def fact(x:Int):Int = if(x==0) 1 else x + fact(x-1)
//  
//  def cube(x:Int):Int = x*x*x
//  
//  def sumFactorial(a:Int, b:Int):Int ={
//    if(a>b) 0 else
//      fact(a) + sumFactorial(a+1, b)
//  }
//  
//  def sumCube(a:Int, b:Int):Int = if(a>b) 0 else cube(a) + sumCube(a+1, b)
//  
//  def sum(f:Int => Int, a:Int, b:Int):Int = if(a>b) 0 else cube(a) + sum(f, a+1, b)
//  
//  def sumFactorial2(a:Int, b:Int):Int = sum(fact, a, b)
//  
//  def sumCube2(a:Int, b:Int):Int = sum(cube, a, b)
//  
//  //Anonymous Function
//  (x:Int) => x*x*x
//  (x:Int, y:Int) => x + y
//  
//  def sumCube3(a:Int, b:Int):Int =
//    sum(x => x*x*x, a, b)
//    
//  //Partilly applied funcion  
//  def sumCube4 = sumCube3(2, _:Int)
//  
//  val what = sumCube4(3)
//  println(what)
  
}