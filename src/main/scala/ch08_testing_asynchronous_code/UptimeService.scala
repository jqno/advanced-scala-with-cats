package ch08_testing_asynchronous_code

import scala.language.higherKinds

class UptimeService[F[_]](client: UptimeClient[F]) {
  def getTotalUptime(hostnames: List[String]): F[Int] =
    ???
}
