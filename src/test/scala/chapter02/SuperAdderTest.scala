package chapter02

import org.scalatest.{FlatSpec, Matchers}

import cats.instances.all._

class SuperAdderTest extends FlatSpec with Matchers {

  behavior of "add"

  it should "sum the content of a list" in {
    SuperAdder.add(List(1, 2, 3)) should be (6)
  }


  behavior of "add2"

  it should "sum the content of a list of int" in {
    SuperAdder.add2(List(1, 2, 3)) should be (6)
  }

  it should "sum the content of a list of option of int" in {
    val in: List[Option[Int]] = List(Some(1), Some(2), Some(3))
    SuperAdder.add2(in) should be (Some(6))
  }


  behavior of "order"

  it should "be able to be summed in add2" in {
    val in = List(Order(10.0, 2.0), Order(2.0, 1.5))
    SuperAdder.add2(in) should be (Order(12.0, 3.5))
  }
}
