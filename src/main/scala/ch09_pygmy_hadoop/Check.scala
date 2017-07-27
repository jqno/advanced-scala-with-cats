package ch09_pygmy_hadoop

import cats.Semigroup
import cats.data.Validated
import cats.data.Validated.{Invalid, Valid}
import cats.syntax.either._
import ch09_pygmy_hadoop.Check._

sealed trait Check[E, A, B] {
  def apply(value: A)(implicit ev: Semigroup[E]): Validated[E, B]

  def map[C](f: B => C): Check[E, A, C] =
    Map(this, f)

  def flatMap[C](f: B => Check[E, A, C]): Check[E, A, C] =
    FlatMap(this, f)

  def andThen[C](that: Check[E, B, C]): Check[E, A, C] =
    AndThen(this, that)
}

object Check {
  def apply[E, A](p: Predicate[E, A]): Check[E, A, A] =
    Pure(p)

  case class Pure[E, A](p: Predicate[E, A]) extends Check[E, A, A] {
    override def apply(value: A)(implicit ev: Semigroup[E]): Validated[E, A] =
      p(value)
  }

  case class Map[E, A, B, C](c: Check[E, A, B], f: B => C) extends Check[E, A, C] {
    override def apply(value: A)(implicit ev: Semigroup[E]): Validated[E, C] =
      c(value).map(f)
  }

  case class FlatMap[E, A, B, C](c: Check[E, A, B], f: B => Check[E, A, C]) extends Check[E, A, C] {
    override def apply(value: A)(implicit ev: Semigroup[E]): Validated[E, C] =
      c(value).withEither(_.flatMap(b => f(b)(value).toEither))
  }

  case class AndThen[E, A, B, C](c1: Check[E, A, B], c2: Check[E, B, C]) extends Check[E, A, C] {
    override def apply(value: A)(implicit ev: Semigroup[E]): Validated[E, C] =
      c1(value) match {
        case Valid(b) => c2(b)
        case Invalid(e) => Invalid(e)
      }
  }
}