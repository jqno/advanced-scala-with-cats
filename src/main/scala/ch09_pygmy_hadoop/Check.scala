package ch09_pygmy_hadoop

import cats.Semigroup
import cats.data.Validated
import cats.syntax.cartesian._

sealed trait Check[E, A] {
  def apply(value: A)(implicit ev: Semigroup[E]): Validated[E, A] = this match {
    case Pure(f) =>
      f(value)
    case And(c1, c2) =>
      (c1(value) |@| c2(value)).map((_, _) => value)
  }

  def and(that: Check[E, A]): Check[E, A] =
    And(this, that)
}

case class Pure[E, A](f: A => Validated[E, A]) extends Check[E, A]
case class And[E, A](c1: Check[E, A], c2: Check[E, A]) extends Check[E, A]
