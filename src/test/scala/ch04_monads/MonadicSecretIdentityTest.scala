package ch04_monads

import ch04_monads.MonadicSecretIdentity.{Id, IdMonad}
import org.scalatest.{FlatSpec, Matchers}

class MonadicSecretIdentityTest extends FlatSpec with Matchers {

  val id = new IdMonad[Int]
  val b: Id[Int] = 4

  behavior of "Id monad"

  it should "do pure" in {
    id.pure(4) should be (4)
  }

  it should "do flatMap" in {
    id.flatMap(b)(x => id.pure(x + 1)) should be (5)
  }

  it should "do map" in {
    id.map(b)(_ + 1) should be (5)
  }
}
