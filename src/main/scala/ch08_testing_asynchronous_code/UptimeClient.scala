package ch08_testing_asynchronous_code

import cats.Id

import scala.concurrent.Future
import scala.language.higherKinds

trait UptimeClient[F[_]] {
  def getUptime(hostname: String): F[Int]
}

trait RealUptimeClient extends UptimeClient[Future] {
  override def getUptime(hostname: String): Future[Int]
}

trait TestUptimeClient extends UptimeClient[Id] {
  override def getUptime(hostname: String): Id[Int]
}
