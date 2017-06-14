package chapter02

import chapter02.truth._
import org.scalatest.{FlatSpec, Matchers}

class TruthTest extends FlatSpec with Matchers {

  behavior of "andMonoid"

  it should "follow the identity law" in {
    implicit val monoid = MonoidInstances.andMonoid

    val results = for {
      b <- booleans
    } yield identityLaw(b)

    results should contain only true
  }

  it should "follow the associativity law" in {
    implicit val monoid = MonoidInstances.andMonoid

    val results = for {
      a <- booleans
      b <- booleans
      c <- booleans
    } yield associativeLaw(a, b, c)

    results should contain only true
  }


  behavior of "orMonoid"

  it should "follow the identity law" in {
    implicit val monoid = MonoidInstances.orMonoid

    val results = for {
      b <- booleans
    } yield identityLaw(b)

    results should contain only true
  }

  it should "follow the associativity law" in {
    implicit val monoid = MonoidInstances.orMonoid

    val results = for {
      a <- booleans
      b <- booleans
      c <- booleans
    } yield associativeLaw(a, b, c)

    results should contain only true
  }


  val booleans = List(true, false)

}
