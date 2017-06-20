package ch03_functors

import cats.syntax.functor._
import org.scalatest.{FlatSpec, Matchers}

class TreeTest extends FlatSpec with Matchers {

  behavior of "Tree functor"

  val tree: Tree[Int] = Branch(Branch(Leaf(1), Leaf(2)), Leaf(3))

  it should "behave mappily" in {
    tree map (_ + 1) should be (Branch(Branch(Leaf(2), Leaf(3)), Leaf(4)))
  }

  it should "follow the identity law" in {
    tree map identity should be (tree)
  }

  it should "follow the composition law" in {
    val first = tree map (_ * 2 + 1)
    val second = tree map (_ * 2) map (_ + 1)
    first should be (second)
  }
}
