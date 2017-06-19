package chapter02

import org.scalatest.{FlatSpec, Matchers}

class SuperAdderTest extends FlatSpec with Matchers {

  behavior of "add"

  it should "sum the content of a list" in {
    SuperAdder.add(List(1, 2, 3)) should be (6)
  }
}
