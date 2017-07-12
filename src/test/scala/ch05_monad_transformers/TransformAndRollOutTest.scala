package ch05_monad_transformers

import ch05_monad_transformers.TransformAndRollOut.getPowerLevel
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}

class TransformAndRollOutTest extends FlatSpec with Matchers with ScalaFutures {
  behavior of "getPowerLevel"

  it should "return the powerlevel if the autobot is reachable" in {
    getPowerLevel("Hot Rod").value.futureValue should be (Right(10))
  }

  it should "return a message if the autobot is unreachable" in {
    getPowerLevel("Ratchet").value.futureValue should be (Left("Ratchet is unavailable"))
  }
}
