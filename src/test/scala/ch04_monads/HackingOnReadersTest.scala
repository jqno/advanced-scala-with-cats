package ch04_monads

import HackingOnReaders._
import org.scalatest.{FlatSpec, Matchers}

class HackingOnReadersTest extends FlatSpec with Matchers {
  behavior of "DbReader"

  val db = Db(
    Map(1 -> "dade", 2 -> "kate", 3 -> "margo"),
    Map("dade" -> "zerocool", "kate" -> "acidburn", "margo" -> "secret"))

  it should "successfully validate a valid username/password combination" in {
    checkLogin(1, "zerocool").run(db) should be (true)
  }

  it should "unsuccessfully validate an invalid username/password combination" in {
    checkLogin(4, "davinci").run(db) should be (false)
  }
}
