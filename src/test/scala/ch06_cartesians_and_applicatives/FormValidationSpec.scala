package ch06_cartesians_and_applicatives

import ch06_cartesians_and_applicatives.FormValidation._
import org.scalatest.{FlatSpec, Matchers}

class FormValidationSpec extends FlatSpec with Matchers {

  val someMap = Map("name" -> "Pietje", "age" -> "42")

  behavior of "getValue"

  it should "get the value of an existing key" in {
    getValue(someMap, "name") should be ("Pietje")
  }
}
