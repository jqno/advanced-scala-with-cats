package ch09_pygmy_hadoop

import cats.data.Validated.{Invalid, Valid}
import cats.instances.list._
import org.scalatest.{FlatSpec, Matchers}

class PredicateTest extends FlatSpec with Matchers {

  val isEven: Predicate[List[String], Int] = Predicate {
    case i if i % 2 == 0 => Valid(i)
    case i => Invalid(List(s"$i is odd"))
  }

  val isPositive: Predicate[List[String], Int] = Predicate {
    case i if i >= 0 => Valid(i)
    case i => Invalid(List(s"$i is negative"))
  }

  val and = isEven and isPositive
  val or = isEven or isPositive


  behavior of "and"

  it should "return the value of two valid values" in {
    and(2) should be (Valid(2))
  }

  it should "return the error when one of the sides is invalid" in {
    and(1) should be (Invalid(List("1 is odd")))
    and(-2) should be (Invalid(List("-2 is negative")))
  }

  it should "combine the errors" in {
    and(-1) should be (Invalid(List("-1 is odd", "-1 is negative")))
  }


  behavior of "or"

  it should "return the value of two valid values" in {
    or(2) should be (Valid(2))
  }

  it should "return the value when one of the sides is valid" in {
    or(1) should be (Valid(1))
    or(-2) should be (Valid(-2))
  }

  it should "combine the errors" in {
    or(-1) should be (Invalid(List("-1 is odd", "-1 is negative")))
  }
}
