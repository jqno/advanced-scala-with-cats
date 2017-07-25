package ch07_foldable_and_traverse

import cats.instances.vector._
import ch07_foldable_and_traverse.TraversingWith._
import org.scalatest.{FlatSpec, Matchers}

class TraversingWithTest extends FlatSpec with Matchers {

  behavior of "Traversing with Vectors"

  it should "1: combine elements from each vector" in {
    val in = List(Vector(1, 2), Vector(3, 4))
    val expected = Vector(List(1, 3), List(1, 4), List(2, 3), List(2, 4))
    listSequence(in) should be (expected)
  }

  it should "2: combine elements from each vector" in {
    val in = List(Vector(1, 2), Vector(3, 4), Vector(5, 6))
    val expected = Vector(List(1, 3, 5), List(1, 3, 6), List(1, 4, 5), List(1, 4, 6),
      List(2, 3, 5), List(2, 3, 6), List(2, 4, 5), List(2, 4, 6))
    listSequence(in) should be (expected)
  }


  behavior of "Traversing with Options"

  it should "have the right return type" in {
    val in = List(1, 2, 3)
    "val q: Option[List[Int]] = process(in)" should compile
  }

  it should "produce the expected output" in {
    process(List(2, 4, 6)) should be (Some(List(2, 4, 6)))
    process(List(1, 2, 3)) should be (None)
  }

}
