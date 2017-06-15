package chapter02

import chapter02.NoCatsYet._
import org.scalatest.{FlatSpec, Matchers}

class TruthTest extends FlatSpec with Matchers {

  behavior of "andMonoid"

  it should "follow the identity law" in {
    checkIdentity(booleans)(TruthInstances.andMonoid) should be (true)
  }

  it should "follow the associativity law" in {
    checkAssociativity(booleans)(TruthInstances.andMonoid) should be (true)
  }


  behavior of "orMonoid"

  it should "follow the identity law" in {
    checkIdentity(booleans)(TruthInstances.orMonoid) should be (true)
  }

  it should "follow the associativity law" in {
    checkAssociativity(booleans)(TruthInstances.orMonoid) should be (true)
  }


  behavior of "xorMonoid"

  it should "follow the identity law" in {
    checkIdentity(booleans)(TruthInstances.xorMonoid) should be (true)
  }

  it should "follow the associativity law" in {
    checkAssociativity(booleans)(TruthInstances.xorMonoid) should be (true)
  }


  behavior of "eqMonoid"

  it should "follow the identity law" in {
    checkIdentity(booleans)(TruthInstances.eqMonoid) should be (true)
  }

  it should "follow the associativity law" in {
    checkAssociativity(booleans)(TruthInstances.eqMonoid) should be (true)
  }

}
