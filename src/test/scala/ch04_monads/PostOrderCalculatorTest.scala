package ch04_monads

import org.scalatest.{FlatSpec, Matchers}
import PostOrderCalculator._

class PostOrderCalculatorTest extends FlatSpec with Matchers {
  behavior of "evalOne"

  it should "parse a digit" in {
    evalOne("4").runA(Nil).value should be (4)
  }

  it should "parse operators" in {
    val init = List(6, 2)
    evalOne("+").runA(init).value should be (8)
    evalOne("*").runA(init).value should be (12)
    evalOne("-").runA(init).value should be (4)
    evalOne("/").runA(init).value should be (3)
  }

  it should "run a program" in {
    val program = for {
      _ <- evalOne("1")
      _ <- evalOne("2")
      ans <- evalOne("+")
    } yield ans
    program.runA(Nil).value should be (3)
  }


  behavior of "evalAll"

  it should "run a sequence of computations" in {
    val input = List("1", "2", "+", "3", "4", "*", "+")
    evalAll(input).runA(Nil).value should be (15)
  }
}
