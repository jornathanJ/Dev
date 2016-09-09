package com.raonbit.edu

import java.util.HashMap

import java.net.SocketAddress;

sealed class Expr
  case class Var(n:String) extends Expr
  case class Number(n:Double) extends Expr
  case class UnOp(n:String, arg:Expr) extends Expr
  case class BiOp(n:String, left:Expr, right:Expr) extends Expr

object sealedClass extends App {

  def findExpr(e:Expr) = e match{
    case Number(n) => println("this is number")
    case Var(n) => println("this is Variable")
    
  }
  
  def findExprUnchecked(e:Expr) = (e: @unchecked) match {
    case Var(n) => println("this is Variable")
    case Number(n) => println("this is number")
  }
   
  
  val v1 = Var("test")
  var v2 = Number(3.14)
  
  
  findExpr(v1)
  findExpr(v2)
  
  findExprUnchecked(v1)
  findExprUnchecked(v2)
  
}
