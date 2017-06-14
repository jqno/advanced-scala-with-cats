package chapter02

import chapter02.truth._
import org.scalatest.{FlatSpec, Matchers}

class TruthTest extends FlatSpec with Matchers {

  behavior of "andMonoid"

  it should "follow the identity law" in {
    checkIdentity(MonoidInstances.andMonoid, booleans)
  }

  it should "follow the associativity law" in {
    checkAssociativity(MonoidInstances.andMonoid, booleans)
  }


  behavior of "orMonoid"

  it should "follow the identity law" in {
    checkIdentity(MonoidInstances.orMonoid, booleans)
  }

  it should "follow the associativity law" in {
    checkAssociativity(MonoidInstances.orMonoid, booleans)
  }


  behavior of "xorMonoid"

  it should "follow the identity law" in {
    checkIdentity(MonoidInstances.xorMonoid, booleans)
  }

  it should "follow the associativity law" in {
    checkAssociativity(MonoidInstances.xorMonoid, booleans)
  }


  behavior of "eqMonoid"

  it should "follow the identity law" in {
    checkIdentity(MonoidInstances.eqMonoid, booleans)
  }

  it should "follow the associativity law" in {
    checkAssociativity(MonoidInstances.eqMonoid, booleans)
  }


  val booleans = List(true, false)

  def checkIdentity[A](monoid: Monoid[A], generator: List[A]): Unit = {
    val results = for {
      a <- generator
    } yield identityLaw(a)(monoid)

    results should contain only true
  }

  def checkAssociativity[A](monoid: Monoid[A], generator: List[A]): Unit = {
    val results = for {
      a <- generator
      b <- generator
      c <- generator
    } yield associativeLaw(a, b, c)(monoid)

    results should contain only true
  }

}
