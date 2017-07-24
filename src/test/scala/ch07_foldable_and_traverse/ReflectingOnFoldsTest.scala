package ch07_foldable_and_traverse

import org.scalatest.{FlatSpec, Matchers}

class ReflectingOnFoldsTest extends FlatSpec with Matchers {

  val someList = List(List(1, 2), List(3))


  behavior of "foldLeft"

  it should "not compile" in {
    "someList.foldLeft(List.empty[List[Int]])(_ :: _)" shouldNot compile
  }


  behavior of "foldRight"

  it should "re-create the list we already had" in {
    someList.foldRight(List.empty[List[Int]])(_ :: _) should be (someList)
  }
}
