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

  def factorial(n: Int): Logged[Int] = {
    for {
      ans <- if (n == 0) 1.pure[Logged] else slowly(factorial(n - 1).map(_ * n))
      _ <- Vector(s"fact $n $ans").tell
    } yield ans
  }

  val result = Await.result(Future.sequence(Vector(
    Future(factorial(3).run),
    Future(factorial(3).run)
  )), 5.seconds)
  result.flatMap(_._1).foreach(println)
}
