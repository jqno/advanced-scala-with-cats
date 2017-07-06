package ch05_monad_transformers

import cats.data.EitherT

import scala.concurrent.Future

object TransformAndRollOut {
  // type Response[A] = Future[Either[String, A]]
  type Response[A] = EitherT[Future, String, A]

  def getPowerLevel(autobot: String): Response[Int] = ???

}
