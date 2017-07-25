package ch07_foldable_and_traverse

import cats.Applicative
import cats.data.Validated
import cats.instances.list._
import cats.instances.option._
import cats.syntax.applicative._
import cats.syntax.cartesian._

import scala.language.higherKinds

object TraversingWith {

  def listTraverse[F[_]: Applicative, A, B](list: List[A])(func: A => F[B]): F[List[B]] =
    list.foldLeft(List.empty[B].pure[F]) { (accum, item) =>
      (accum |@| func(item)).map(_ :+ _)
    }

  def listSequence[F[_]: Applicative, A](list: List[F[A]]): F[List[A]] =
    listTraverse(list)(identity)

  def processToOption(inputs: List[Int]) =
    listTraverse(inputs)(n => if (n % 2 == 0) Some(n) else None)

  type ErrorsOr[A] = Validated[List[String], A]
  def processToValidated(inputs: List[Int]): ErrorsOr[List[Int]] =
    listTraverse(inputs) { n =>
      if (n % 2 == 0)
        Validated.valid(n)
      else
        Validated.invalid(List(s"$n is not even"))
    }

}
