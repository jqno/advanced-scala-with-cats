package ch04_monads

import org.scalatest.{FlatSpec, Matchers}
import SaferFoldingWithEval._

class SaferFoldingWithEvalTest extends FlatSpec with Matchers {

  val lst = List(1, 2, 3)
  val init = 3

  behavior of "foldRight1 (sanity check)"

  it should "sum with an initial value" in {
    foldRight1(lst, init)(_ + _) should be (9)
  }


  behavior of "foldRight2 (with Eval monad)"

  it should "sum with an initial value" in {
    foldRight2(lst, init)(_ + _) should be (9)
  }
}
