package ch09_pygmy_hadoop

import cats.Semigroup
import cats.syntax.semigroup._

sealed trait Check[E, A] {
  def apply(value: A)(implicit ev: Semigroup[E]): Either[E, A] = this match {
    case Pure(f) => f(value)
    case And(c1, c2) => (c1(value), c2(value)) match {
      case (Right(_), Right(_)) => Right(value)
      case (left, Right(_)) => left
      case (Right(_), left) => left
      case (Left(e1), Left(e2)) => Left(e1 combine e2)
    }
  }

  def and(that: Check[E, A]): Check[E, A] =
    And(this, that)
}

case class Pure[E, A](f: A => Either[E, A]) extends Check[E, A]
case class And[E, A](c1: Check[E, A], c2: Check[E, A]) extends Check[E, A]
