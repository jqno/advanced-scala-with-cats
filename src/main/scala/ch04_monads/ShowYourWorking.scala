package ch04_monads

import cats.data.Writer
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
    def loop(logged: Logged[Int]): Logged[Int] = {
      slowly {
        for {
          n <- logged
          prev <- loop((n - 1).pure[Logged])
          ans = n * prev
          _ <- Vector(s"fact $n $ans").tell
        } yield ans
      }
    }

    val (log, ans) = loop(n.pure[Logged]).run
    log.foreach(println)
    ans
  }

  Await.result(Future.sequence(Vector(
    Future(factorial(3)),
    Future(factorial(3))
  )), 5.seconds)
}
