package ch09_pygmy_hadoop

import cats.instances.list._
import org.scalatest.{FlatSpec, Matchers}

class CheckTest extends FlatSpec with Matchers {

  val isEven: Check[List[String], Int] = Pure {
    case i if i % 2 == 0 => Right(i)
    case i => Left(List(s"$i is odd"))
  }

  val isPositive: Check[List[String], Int] = Pure {
    case i if i >= 0 => Right(i)
    case i => Left(List(s"$i is negative"))
  }

  val check = isEven and isPositive


  behavior of "and"

  it should "return the value of two Rights" in {
    check(2) should be (Right(2))
  }

  it should "return the error when one of the sides is a Left" in {
    check(1) should be (Left(List("1 is odd")))
    check(-2) should be (Left(List("-2 is negative")))
  }

  it should "combine the errors" in {
    check(-1) should be (Left(List("-1 is odd", "-1 is negative")))
  }
}
