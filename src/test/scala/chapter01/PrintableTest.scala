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
}
