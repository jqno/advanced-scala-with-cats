package ch09_pygmy_hadoop

import cats.Semigroup
import cats.data.Validated
import cats.data.Validated.{Invalid, Valid}
import cats.syntax.either._
import ch09_pygmy_hadoop.CheckNOPE._

sealed trait CheckNOPE[E, A, B] {
  def apply(value: A)(implicit ev: Semigroup[E]): Validated[E, B]

  def map[C](f: B => C): CheckNOPE[E, A, C] =
    Map(this, f)

  def flatMap[C](f: B => CheckNOPE[E, A, C]): CheckNOPE[E, A, C] =
    FlatMap(this, f)

  def andThen[C](that: CheckNOPE[E, B, C]): CheckNOPE[E, A, C] =
    AndThen(this, that)
}

object CheckNOPE {
  def apply[E, A, B](f: A => Validated[E, B]): CheckNOPE[E, A, B] =
    Pure(f)

  def apply[E, A](p: Predicate[E, A]): CheckNOPE[E, A, A] =
    PurePredicate(p)

  case class Pure[E, A, B](f: A => Validated[E, B]) extends CheckNOPE[E, A, B] {
    override def apply(value: A)(implicit ev: Semigroup[E]): Validated[E, B] =
      f(value)
  }

  case class PurePredicate[E, A](p: Predicate[E, A]) extends CheckNOPE[E, A, A] {
    override def apply(value: A)(implicit ev: Semigroup[E]): Validated[E, A] =
      p(value)
  }

  case class Map[E, A, B, C](c: CheckNOPE[E, A, B], f: B => C) extends CheckNOPE[E, A, C] {
    override def apply(value: A)(implicit ev: Semigroup[E]): Validated[E, C] =
      c(value).map(f)
  }

  case class FlatMap[E, A, B, C](c: CheckNOPE[E, A, B], f: B => CheckNOPE[E, A, C]) extends CheckNOPE[E, A, C] {
    override def apply(value: A)(implicit ev: Semigroup[E]): Validated[E, C] =
      c(value).withEither(_.flatMap(b => f(b)(value).toEither))
  }

  case class AndThen[E, A, B, C](c1: CheckNOPE[E, A, B], c2: CheckNOPE[E, B, C]) extends CheckNOPE[E, A, C] {
    override def apply(value: A)(implicit ev: Semigroup[E]): Validated[E, C] =
      c1(value) match {
        case Valid(b) => c2(b)
        case Invalid(e) => Invalid(e)
      }
  }
}