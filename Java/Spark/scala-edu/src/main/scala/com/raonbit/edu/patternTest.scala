package com.raonbit.edu

import java.util.HashMap

import java.net.SocketAddress;


object patternTest extends App {
  
  val times = 1
  times match{
    case 1 => println("one")
    case _ => println("other")
  }
  
  val obj:Object = "ABC"
  obj match{
    case str:String => println("String")
    case addr:SocketAddress => println("SocketAddress")
  }
  
  val list:List[String]=List("A", "B", "C")
  
  val x = list match{
    case head :: _=> head
    case Nil => "Nothing"
  }
  
  println("println(x)"); println(x)
  
  val niList:List[String]=List()
  
  val y = niList match{
    case head :: _=> head
    case Nil => "Nothing"
  }
  
//  printf("text",
//  printf("text", list)
  
  printf("println(y)" , println(y))
  //s; println(y)
  
  val xa = list.headOption getOrElse "Nothing"
  
  println("println(xa)"); println(xa)
  
  val ya = niList.headOption getOrElse "Nothing"
  
  println("println(ya)"); println(ya)
  
   
}