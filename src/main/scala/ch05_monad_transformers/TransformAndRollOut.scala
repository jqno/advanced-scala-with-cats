package ch05_monad_transformers

import cats.data.EitherT

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

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
}
