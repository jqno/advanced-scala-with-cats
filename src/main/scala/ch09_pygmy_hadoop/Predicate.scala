package ch09_pygmy_hadoop

import cats.Semigroup
import cats.data.Validated
import cats.data.Validated.{Invalid, Valid}
import cats.syntax.cartesian._
import cats.syntax.semigroup._
import cats.syntax.validated._
import ch09_pygmy_hadoop.Predicate._

sealed trait Predicate[E, A] {
  def apply(value: A)(implicit ev: Semigroup[E]): Validated[E, A] = this match {
    case Pure(f) =>
      f(value)
    case And(c1, c2) =>
      (c1(value) |@| c2(value)).map((_, _) => value)
    case Or(c1, c2) => (c1(value), c2(value)) match {
      case (Valid(_), _) => Valid(value)
      case (_, Valid(_)) => Valid(value)
      case (Invalid(v1), Invalid(v2)) => Invalid(v1 combine v2)
    }
  }

  def run(implicit ev: Semigroup[E]): A => Either[E, A] =
    (a: A) => apply(a).toEither

  def and(that: Predicate[E, A]): Predicate[E, A] =
    And(this, that)

  def or(that: Predicate[E, A]): Predicate[E, A] =
    Or(this, that)
}

object Predicate {
  def apply[E, A](f: A => Validated[E, A]): Predicate[E, A] =
    Pure(f)

  def lift[E, A](error: E, f: A => Boolean): Predicate[E, A] =
    Pure(a => if (f(a)) a.valid else error.invalid)

  case class Pure[E, A](f: A => Validated[E, A]) extends Predicate[E, A]
  case class And[E, A](c1: Predicate[E, A], c2: Predicate[E, A]) extends Predicate[E, A]
  case class Or[E, A](c1: Predicate[E, A], c2: Predicate[E, A]) extends Predicate[E, A]
}
