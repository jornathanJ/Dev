package com.raonbit.edu

object caseClass extends App {

  abstract class Term
  case class Var(name: String) extends Term {
  
    def test(a:Int): Int= {
      println("test")
      a
    }
    
  }
  case class Fun(arg: String, body: Term) extends Term
  case class App(f: Term, v: Term) extends Term

  Fun("x", Fun("y", App(Var("x"), Var("y"))))

  def printTerm(term: Term) {
    term match {
      case Var(n) => print(n)
      case Fun(x, b) =>
        print("^" + x + "."); printTerm(b)
      case App(a, b) => printTerm(a); printTerm(b)
    }
  }

  printTerm(Var("x"))
  println()
  printTerm(Fun("x", Fun("y", App(Var("x"), Var("y")))))
  
  
  def printLabel(v: Var){
    v match{
      case Var("A") => println("First")
      case Var("B") => println("Seconds")
      case _ => println("Nothing")
    }
  }
  
  println()
  
//  Term a = Var("a")
//  var a = Var("a")
//  printLabel(a)
//  val obj:Object = Var("a")
  
  printLabel(Var("A"))
  printLabel(Var("B"))
  printLabel(Var("D"))
  
}