package chapter02

import org.scalatest.{FlatSpec, Matchers}
import NoCatsYet._

class SetTest extends FlatSpec with Matchers {

  behavior of "appendMonoid"

  it should "follow the identity law" in {
    checkIdentity(sets)(SetInstances.appendMonoid) should be (true)
  }

  it should "follow the associativity law" in {
    checkAssociativity(sets)(SetInstances.appendMonoid) should be (true)
  }


  behavior of "unionMonoid"

  it should "follow the identity law" in {
    checkIdentity(sets)(SetInstances.unionMonoid) should be (true)
  }

  it should "follow the associativity law" in {
    checkAssociativity(sets)(SetInstances.unionMonoid) should be (true)
  }


  behavior of "intersectSemigroup"

  it should "follow the associativity law" in {
    checkAssociativity(sets)(SetInstances.intersectSemigroup) should be (true)
  }
}
