package chapter02

import chapter02.NoCatsYet._
import org.scalatest.{FlatSpec, Matchers}

class TruthTest extends FlatSpec with Matchers {

  behavior of "andMonoid"

  it should "follow the identity law" in {
    checkIdentity(TruthInstances.andMonoid, booleans) should be (true)
  }

  it should "follow the associativity law" in {
    checkAssociativity(TruthInstances.andMonoid, booleans) should be (true)
  }


  behavior of "orMonoid"

  it should "follow the identity law" in {
    checkIdentity(TruthInstances.orMonoid, booleans) should be (true)
  }

  it should "follow the associativity law" in {
    checkAssociativity(TruthInstances.orMonoid, booleans) should be (true)
  }


  behavior of "xorMonoid"

  it should "follow the identity law" in {
    checkIdentity(TruthInstances.xorMonoid, booleans) should be (true)
  }

  it should "follow the associativity law" in {
    checkAssociativity(TruthInstances.xorMonoid, booleans) should be (true)
  }


  behavior of "eqMonoid"

  it should "follow the identity law" in {
    checkIdentity(TruthInstances.eqMonoid, booleans) should be (true)
  }

  it should "follow the associativity law" in {
    checkAssociativity(TruthInstances.eqMonoid, booleans) should be (true)
  }

}
