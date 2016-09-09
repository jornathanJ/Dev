package com.raonbit.edu

import java.util.HashMap

import java.net.SocketAddress;

case class Book(title:String, authors:List[String])

object queryWithForStatement extends App {


  val books:List[Book] = List(
      Book("A", List("KIM", "LEE")),
      Book("B", List("KIM", "LEE", "Park")),
      Book(authors = List("Park", "Jeong", "Choi"), title = "C")
      )
      
  val authors = for {
    b1 <- books
    b2 <- books
    if b1.title != b2.title
      a1 <- b1.authors
      a2 <- b2.authors
      if a1 != a2
  } yield a1
  
  println("All authors")
  authors.foreach(printf("%s,", _))
  
  println("")
  println("==================")
  
  val uniqueauthors ={ for {
    b1 <- books
    b2 <- books
    if b1.title != b2.title
      a1 <- b1.authors
      a2 <- b2.authors
      if a1 != a2
  } yield a1 }.distinct
  
  println("unique authors")
  uniqueauthors.foreach(printf("%s,", _))
  
  println("")
  println("==================")
  
  
  val bookSet = books.toSet
  
//  val setAuthors = for {
//    b1 <- books
//    b2 <- books
//    if b1.title != b2.title
//      a1 <- b1.authors
//      a2 <- b2.authors
//      if a1 != a2
//  } yield a1
  
  val setAuthors = for {
    b1 <- bookSet
    b2 <- bookSet
    if b1.title != b2.title
      a1 <- b1.authors
      a2 <- b2.authors
      if a1 != a2
  } yield a1
  
  println("setAuthors ")
  setAuthors.foreach(printf("%s,", _))
  
  //  setAuthors 
  //KIM,KIM,LEE,LEE,KIM,KIM,KIM,LEE,LEE,LEE,KIM,LEE,Park,Park,KIM,KIM,KIM,LEE,LEE,LEE,Park,Park,Park,Park,Jeong,Jeong,Choi,Choi,Park,Park,Jeong,Jeong,Jeong,Choi,Choi,Choi,
}
