package com.raonbit.edu



object constantVariableMatch extends App {

  import java.util.HashMap
  import math._

  E match {
    case Pi => println("this is Pi")
    case _ => println("this is not Pi")
  }

  val pi = Pi
  E match {
    case pi => println("this is Pi")
    case _ => println("this is not Pi")
  }
  
  // ` means bring to constanst value so, `pi` is real constanst value = 3.14....
  Pi match {
    case `pi` => println("this is Pi")
    case _ => println("this is not Pi")
  }
  
  Pi match {
    case this.pi => println("this is Pi")
    case _ => println("this is not Pi")
  }
  
  val b = "b"
  b match {
    case a => println("this is a")
    case "b" => println("this is b")
  }
  
  

}
