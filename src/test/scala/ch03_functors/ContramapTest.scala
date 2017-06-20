package ch03_functors

import ch03_functors.contramap.Box
import org.scalatest.{FlatSpec, Matchers}

class ContramapTest extends FlatSpec with Matchers {

  behavior of "contramap"

  it should "have a sanitycheck for Printable[Int]" in {
    contramap.format("4") should be ("\"4\"")
  }

  it should "turn a stringPrintable into an intPrintable" in {
    implicit val intPrintable = contramap.stringPrintable.contramap[Int](_.toString)
    contramap.format(4) should be ("\"4\"")
  }

  it should "print a Boxed boolean" in {
    contramap.format(Box(true)) should be ("yes")
  }
}
