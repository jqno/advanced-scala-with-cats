package ch09_pygmy_hadoop

import cats.data.Kleisli
import cats.instances.all._
import cats.syntax.all._
import ch09_pygmy_hadoop.Predicates._

object Checks {

  type Result[A] = Either[Errors, A]

  type Check[A, B] = Kleisli[Result, A, B]

  def check[A, B](f: A => Result[B]): Check[A, B] =
    Kleisli(f)

  def checkPred[A](p: Predicate[Errors, A]): Check[A, A] =
    Kleisli[Result, A, A](p.run)

  val usernameCheck: Check[String, String] = checkPred {
    longerThan(3) and alphanumeric
  }

  val emailCheck: Check[String, String] =
    checkPred(contains('@')).flatMap { s =>
      val (left :: right :: Nil) = s.split('@').toList
      val l = checkPred(longerThan(0)).run.apply(left)
      val r = checkPred(longerThan(2) and contains('.')).run.apply(right)
      println(s"$l |+| $r")
      check(_ => (l.toValidated |+| r.toValidated).toEither)
    }
}
