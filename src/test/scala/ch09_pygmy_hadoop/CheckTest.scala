package ch09_pygmy_hadoop

import cats.data.Validated.{Invalid, Valid}
import cats.instances.list._
import org.scalatest.{FlatSpec, Matchers}

class CheckTest extends FlatSpec with Matchers {

  val isEven: Check[List[String], Int] = Pure {
    case i if i % 2 == 0 => Valid(i)
    case i => Invalid(List(s"$i is odd"))
  }

  val isPositive: Check[List[String], Int] = Pure {
    case i if i >= 0 => Valid(i)
    case i => Invalid(List(s"$i is negative"))
  }

  val check = isEven and isPositive


  behavior of "and"

  it should "return the value of two valid values" in {
    check(2) should be (Valid(2))
  }

  it should "return the error when one of the sides is invalid" in {
    check(1) should be (Invalid(List("1 is odd")))
    check(-2) should be (Invalid(List("-2 is negative")))
  }

  it should "combine the errors" in {
    check(-1) should be (Invalid(List("-1 is odd", "-1 is negative")))
  }
}
