package ch09_pygmy_hadoop

import cats.Semigroup
import cats.data.Validated
import cats.data.Validated.{Invalid, Valid}
import cats.syntax.cartesian._
import cats.syntax.semigroup._

sealed trait Check[E, A] {
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

  def and(that: Check[E, A]): Check[E, A] =
    And(this, that)

  def or(that: Check[E, A]): Check[E, A] =
    Or(this, that)
}

case class Pure[E, A](f: A => Validated[E, A]) extends Check[E, A]
case class And[E, A](c1: Check[E, A], c2: Check[E, A]) extends Check[E, A]
case class Or[E, A](c1: Check[E, A], c2: Check[E, A]) extends Check[E, A]
