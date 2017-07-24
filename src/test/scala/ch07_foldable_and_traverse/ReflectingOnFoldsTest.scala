package ch07_foldable_and_traverse

import org.scalatest.{FlatSpec, Matchers}

class ReflectingOnFoldsTest extends FlatSpec with Matchers {

  val someList = List(1, 2, 3)


  behavior of "foldLeft"

  it should "reverse the list" in {
    someList.foldLeft(List.empty[Int])((a, i) => i :: a) should be (someList.reverse)
  }


  behavior of "foldRight"

  it should "re-create the list we already had" in {
    someList.foldRight(List.empty[Int])((i, a) => i :: a) should be (someList)
  }
}
