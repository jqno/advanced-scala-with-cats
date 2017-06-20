package ch01_typeclasses

import cats.instances.option._
import cats.syntax.eq._
import cats.syntax.option._
import ch01_typeclasses.EqInstances._
import org.scalatest.{FlatSpec, Matchers, NonImplicitAssertions}

class EqTest extends FlatSpec with Matchers with NonImplicitAssertions {

  behavior of "Eq"

  val cat1 = Cat("Garfield", 35, "orange and black")
  val cat2 = Cat("Heathcliff", 30, "orange and black")

  it should "compare Cats" in {
    cat1 === cat1 should be (true)
    cat1 === cat2 should be (false)
  }

  it should "compare Schrödinger's Cats" in {
    cat1.some === cat1.some should be (true)
    cat1.some === cat2.some should be (false)
    cat1.some === Option.empty should be (false)
  }

  it should "not compare regular cats with optional ones" in {
    "cat1 === cat1.some" shouldNot compile
  }
}
