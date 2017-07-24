package ch07_foldable_and_traverse

import org.scalatest.{FlatSpec, Matchers}
import ScafFoldingOtherMethods._

class ScafFoldingOtherMethodsTest extends FlatSpec with Matchers {

  val someList = List(1, 2, 3)


  behavior of "map"

  it should "behave as expected" in {
    map(someList)(_ + 1) should be (List(2, 3, 4))
  }


  behavior of "flatMap"

  it should "behave as expected" in {
    flatMap(someList)(i => List(i + 1)) should be (List(2, 3, 4))
  }


  behavior of "filter"

  it should "behave as expected" in {
    filter(someList)(_ % 2 == 0) should be (List(2))
  }
  

  behavior of "sum"

  it should "behave as expected" in {
    sum(someList) should be (6)
  }
}
