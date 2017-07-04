package ch04_monads

import cats.data.Writer
import cats.instances.vector._
import cats.syntax.applicative._
import cats.syntax.writer._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationLong
import scala.concurrent.{Await, Future}

object ShowYourWorking extends App {

  type Logged[A] = Writer[Vector[String], A]

  def slowly[A](body: => A): A =
    try body finally Thread.sleep(100)

  def factorial(n: Int): Int = {
    def loop(nn: Int): Logged[Int] = {
      for {
        ans <- if (nn == 0) 1.pure[Logged] else slowly(loop(nn - 1).map(_ * nn))
        _ <- Vector(s"fact $nn $ans").tell
      } yield ans
    }

    val (log, ans) = loop(n).run
    log.foreach(println)
    ans
  }

  Await.result(Future.sequence(Vector(
    Future(factorial(3)),
    Future(factorial(3))
  )), 5.seconds)
}
