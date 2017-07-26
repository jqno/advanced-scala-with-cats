package ch09_pygmy_hadoop

import cats.Semigroup
import cats.syntax.semigroup._

sealed trait Check[E, A] {
  def and(that: Check[E, A])(implicit ev: Semigroup[E]): Check[E, A] = (this, that) match {
    case (Right(_), Right(_)) => this
    case (left, Right(_)) => left
    case (Right(_), left) => left
    case (Left(l1), Left(l2)) => Left(l1 combine l2)
  }
}

case class Left[E, A](error: E) extends Check[E, A]
case class Right[E, A](value: A) extends Check[E, A]