package ch06_cartesians_and_applicatives

import ch06_cartesians_and_applicatives.FormValidation._
import org.scalatest.{FlatSpec, Matchers}

class FormValidationSpec extends FlatSpec with Matchers {

  val someMap = Map("name" -> "Pietje", "age" -> "42")


  behavior of "getValue"

  it should "get the value of an existing key" in {
    getValue(someMap, "name") should be (Right("Pietje"))
  }

  it should "give an error message when the fieldName does not exist" in {
    getValue(someMap, "invalid") should be (Left(List("Field name invalid not specified")))
  }


  behavior of "parseInt"

  it should "parse an int value" in {
    parseInt(someMap, "age") should be (Right(42))
  }

  it should "give an error message when the field is not an int" in {
    parseInt(someMap, "name") should be (Left(List("Value Pietje in name was not an int")))
  }
}
