package com.raonbit.edu

import java.util.HashMap

import java.net.SocketAddress;

object tupleOption extends App {

  val server = ("localhost", 80, "/root")

  printf("Context Path is %s", server._3)
  println()

  val service = server match {
    case ("localhost", 80, "/root") => "web"
    case ("localhost", 3306, "") => "MySql"
  }

  
  val(host, port, context) = server
  printf("Host name is %s", host)
  
  
  val newserver = "localhost" -> 80
  
  println()
  
  var username: Option[String] = None
  username = Some("Park")
  username match{
    case Some(value) => print(value)
    case None => print("Nothing")
  }
  
//  val name: Option[String] = request getParameter "name"
//  val upper = name map { _.trim } filter { _.length != 0 } map { _.toUpperCase }
//  println(upper getOrElse "")
      
}
