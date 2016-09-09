package com.raonbit.edu

import java.util.HashMap

import java.net.SocketAddress;

object combinator extends App {
  
  def pr(text:String, xs:Any*){
    println()
    printf("[Result of ]" + text + " ----> ")
    println(xs)
    //println("")
  }

  //val list = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
  val list = 1 to 10

//  println("list========================")
//  val a = list.map((i: Int) => i * 2)
//  println(a)
  
  pr("list.map((i: Int) => i * 2)", list.map((i: Int) => i * 2))

  pr("list.map(x => x * 2)", list.map(x => x * 2))
//  val b = list.map(x => x * 2)
//  println(b)

  def square(x: Int): Int = x * x
  val a1 = list.map((x: Int) => square(x))
  val a2 = list.map(x => square(x))
  val a3 = list.map(square(_))
  val a4 = list.map(square)
  
  println("foreach========================")
  val c = list.foreach(_ *2)
  println(c)
  list.foreach(i => println(i*2))
  
  println("filter========================")
  val d = list.filter(_%2 == 0)
  println(d)
  
  println("Zip========================")
  val e = list.zip(List("a", "b", "c", "d"))
  println(d)

  println("Partition========================")
  val f = list.partition(_%2 == 0)
  println(f)
  
  println("Find========================")
  val g = list.find(_>2)
  println(g)
  
  println("Drop========================")
  val h = list.drop(2)
  println(h)
  
  println("Dropwhile========================")
  val i = list.dropWhile(_%2 != 0)
  println(i)
  
  //println("-----------------------fold_Left-----------------------")
  pr("list.foldLeft(0)(_-_)", list.foldLeft(0)(_-_))
  pr("list.foldLeft(0)((m:Int, n:Int) => m-n)", list.foldLeft(0)((m:Int, n:Int) => m-n))
  pr("(0/:list)(_-_)", (0/:list)(_-_))
  
  pr("list.reduceLeft(_-_)", list.reduceLeft(_-_))
  
  pr("list.foldRight(0)(_-_))", list.foldRight(0)(_-_))
  pr("(list :\\ 0)(_-_)", (list :\ 0)(_-_))
  pr("list.reduceRight(_-_)", list.reduceRight(_-_))
  
  pr("List(List(1, 2), List(\"a\", \"b\"), List(0.1, 0.7)).flatten", List(List(1, 2), List("a", "b"), List(0.1, 0.7)).flatten)
  pr("List(List(1, 2), List(3, 4).flatMap(_.map(i => i*2)", List(List(1, 2), List(3, 4)).flatMap(_.map(i => i*2)))
  
  val charatersList = 'a' to 'c'
  
  var perms = charatersList.flatMap( a=> {
    charatersList.flatMap(b => {
      if( a != b) Seq("%c%c".format(a, b)) else Seq()
    })
  }
  )
  
  //중간에 '.'을 떼어 먹어도 상관이 없다.
  var perms2 = charatersList flatMap( a=> {
    charatersList.flatMap(b => {
      if( a != b) Seq("%c%c".format(a, b)) else Seq()
    })
  }
  )
  
  var perms3 = for {
    a <- charatersList
      b <- charatersList
      if a != b
  } yield "%c%c".format(a, b)
  
  pr("perms", perms)
  //println(Seq("%c%c".format('a', 'b')))
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}
