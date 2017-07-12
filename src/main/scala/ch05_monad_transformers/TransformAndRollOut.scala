package ch05_monad_transformers

import cats.data.EitherT
import cats.instances.all._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object TransformAndRollOut {
  // type Response[A] = Future[Either[String, A]]
  type Response[A] = EitherT[Future, String, A]

  val powerLevels = Map(
    "Jazz" -> 6,
    "Bumblebee" -> 8,
    "Hot Rod" -> 10
  )

  def getPowerLevel(autobot: String): Response[Int] =
    EitherT.apply[Future, String, Int](
      Future(powerLevels.get(autobot).toRight(s"$autobot is unavailable")))

  def canSpecialMove(ally1: String, ally2: String): Response[Boolean] = for {
    level1 <- getPowerLevel(ally1)
    level2 <- getPowerLevel(ally2)
  } yield level1 + level2 > 15

  def tacticalReport(ally1: String, ally2: String): String =
    Await.result(canSpecialMove(ally1, ally2).value, 1.second) match {
      case Left(errorMessage) => s"Comms error: $errorMessage"
      case Right(true) => s"$ally1 and $ally2 are ready to roll out!"
      case Right(false) => s"$ally1 and $ally2 need a recharge"
    }
}
