package ch07_foldable_and_traverse

import cats.Applicative
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

}
