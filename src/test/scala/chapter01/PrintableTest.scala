package chapter01

import org.scalatest.{FlatSpec, Matchers}
import PrintableInstances._

class PrintableTest extends FlatSpec with Matchers {

  behavior of "Printable"

  it should "format a String" in {
    Printable.format("string") should be ("string")
  }

  it should "format an Int" in {
    Printable.format(42) should be ("42")
  }

  it should "format a Cat" in {
    val cat = Cat("Sammie", 10, "brown")
    Printable.format(cat) should be ("Sammie is a 10 year old brown cat.")
  }
}
