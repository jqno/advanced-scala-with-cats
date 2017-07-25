package ch08_testing_asynchronous_code

import org.scalatest.{FlatSpec, Matchers}

class UptimeServiceTest extends FlatSpec with Matchers {

  val hosts = Map("host1" -> 10, "host2" -> 6)
  val client = new TestUptimeClient(hosts)
  val service = new UptimeService(client)


  behavior of "UptimeService"

  it should "have the expected uptime" in {
    val expected = hosts.values.sum
    val actual = service.getTotalUptime(hosts.keys.toList)

    actual should be (expected)
  }
}
