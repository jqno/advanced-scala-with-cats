package ch03_functors

import org.scalatest.{FlatSpec, Matchers}
import imap._

class ImapTest extends FlatSpec with Matchers {

  behavior of "imap"

  it should "encode Boxes to Strings" in {
    encode(Box(1)) should be ("1")
  }

  it should "decode Strings to Boxes" in {
    decode[Box[Int]]("1") should be (Some(Box(1)))
  }
}
