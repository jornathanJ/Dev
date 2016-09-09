package com.raonbit.edu

import java.util.HashMap

import java.net.SocketAddress;



object listMax extends App {


  val list = List(5, 2, 1, 9, 120, 2);
  
  
  def max(xs:List[Int]): Int = xs match {
      case Nil =>  throw new RuntimeException("Empty List")
      case head :: Nil => head
      case list => 
        val maxvalue = max(list.tail)
        if(list.head > maxvalue) list.head else maxvalue
  }
}
