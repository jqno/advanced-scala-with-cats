package ch05_monad_transformers

import ch05_monad_transformers.TransformAndRollOut._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}

class TransformAndRollOutTest extends FlatSpec with Matchers with ScalaFutures {
  behavior of "getPowerLevel"

  it should "return the powerlevel if the autobot is reachable" in {
    getPowerLevel("Hot Rod").unwrap should be (10)
  }

  it should "return a message if the autobot is unreachable" in {
    getPowerLevel("Ratchet").unwrapError should be ("Ratchet is unavailable")
  }


  behavior of "canSpecialMove"

  it should "allow a special move if the allies have a combined powerlevel of over 15" in {
    canSpecialMove("Bumblebee", "Hot Rod").unwrap should be (true)
  }

  it should "not allow special move if the allies are weak" in {
    canSpecialMove("Bumblebee", "Jazz").unwrap should be (false)
  }

  it should "fail with a message if either autobot is unreachable" in {
    canSpecialMove("Bumblebee", "Ratchet").unwrapError should be ("Ratchet is unavailable")
  }


  behavior of "tacticalReport"

  it should "allow a special move if the allies have a combined powerlevel of over 15" in {
    tacticalReport("Bumblebee", "Hot Rod") should be ("Bumblebee and Hot Rod are ready to roll out!")
  }

  it should "not allow special move if the allies are weak" in {
    tacticalReport("Bumblebee", "Jazz") should be ("Bumblebee and Jazz need a recharge")
  }

  it should "fail with a message if either autobot is unreachable" in {
    tacticalReport("Bumblebee", "Ratchet") should be ("Comms error: Ratchet is unavailable")
  }


  implicit class UnwrapForTest[A](value: Response[A]) {
    def unwrap: A = value.value.futureValue.right.get
    def unwrapError: String = value.value.futureValue.left.get
  }
}
