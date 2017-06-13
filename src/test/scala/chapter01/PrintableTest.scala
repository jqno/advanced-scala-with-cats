package chapter01

import chapter01.PrintableInstances._
import chapter01.PrintableSyntax._
import org.scalatest.{FlatSpec, Matchers}

class PrintableTest extends FlatSpec with Matchers {

  behavior of "Printable"

  it should "format a String" in {
    // Can't use pretty syntax here,
    // because String already has a format method which conflicts.
    Printable.format("string") should be ("string")
  }

  it should "format an Int" in {
    42.format should be ("42")
  }

  it should "format a Cat" in {
    val cat = Cat("Sammie", 10, "brown")
    cat.format should be ("Sammie is a 10 year old brown cat.")
  }
}
