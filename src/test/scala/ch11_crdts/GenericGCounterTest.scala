package ch11_crdts

import org.scalatest.{FlatSpec, Matchers}

class GenericGCounterTest extends FlatSpec with Matchers {

  val initial = Map(
    "A" -> 2,
    "B" -> 4,
    "C" -> 3
  )
  val someGCounter = GenericGCounter(initial)


  behavior of "increment"

  it should "increase the counter for the given machine" in {
    val actual = someGCounter.increment("B", 2).counters
    actual("B") should be (6)
    actual("A") should be (2)
    actual("C") should be (3)
  }


  behavior of "get"

  it should "sum all the counters" in {
    someGCounter.get should be (9)
  }


  behavior of "merge"

  it should "combine the values of two GCounters" in {
    val otherGCounter = GenericGCounter(Map("B" -> 6, "C" -> 2, "D" -> 1))
    val actual = (someGCounter merge otherGCounter).counters
    actual("A") should be (2)
    actual("B") should be (6)
    actual("C") should be (3)
    actual("D") should be (1)
  }
}
