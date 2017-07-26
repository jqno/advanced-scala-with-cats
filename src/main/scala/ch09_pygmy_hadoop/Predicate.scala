package ch09_pygmy_hadoop

import cats.Semigroup
import cats.data.Validated
import cats.data.Validated.{Invalid, Valid}
import cats.syntax.cartesian._
import cats.syntax.semigroup._

sealed trait Predicate[E, A] {
  def apply(value: A)(implicit ev: Semigroup[E]): Validated[E, A] = this match {
    case PurePredicate(f) =>
      f(value)
    case And(c1, c2) =>
      (c1(value) |@| c2(value)).map((_, _) => value)
    case Or(c1, c2) => (c1(value), c2(value)) match {
      case (Valid(_), _) => Valid(value)
      case (_, Valid(_)) => Valid(value)
      case (Invalid(v1), Invalid(v2)) => Invalid(v1 combine v2)
    }
  }

  def and(that: Predicate[E, A]): Predicate[E, A] =
    And(this, that)

  def or(that: Predicate[E, A]): Predicate[E, A] =
    Or(this, that)
}

case class PurePredicate[E, A](f: A => Validated[E, A]) extends Predicate[E, A]
case class And[E, A](c1: Predicate[E, A], c2: Predicate[E, A]) extends Predicate[E, A]
case class Or[E, A](c1: Predicate[E, A], c2: Predicate[E, A]) extends Predicate[E, A]
