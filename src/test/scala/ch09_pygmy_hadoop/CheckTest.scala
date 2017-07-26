package ch09_pygmy_hadoop

import cats.instances.string._
import org.scalatest.{FlatSpec, Matchers}

class CheckTest extends FlatSpec with Matchers {

  val right: Check[String, String] = Right("value")
  val left1: Check[String, String] = Left("a")
  val left2: Check[String, String] = Left("b")

  behavior of "and"

  it should "return the value of two Rights" in {
    (right and right) should be (right)
  }

  it should "return the error when one of the sides is a Left" in {
    (left1 and right) should be (left1)
    (right and left2) should be (left2)
  }

  it should "combine the errors" in {
    (left1 and left2) should be (Left("ab"))
  }
}
