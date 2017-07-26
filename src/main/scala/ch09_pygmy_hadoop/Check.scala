package ch09_pygmy_hadoop

import cats.Semigroup
import cats.data.Validated

sealed trait Check[E, A, B] {
  def apply(value: A)(implicit ev: Semigroup[E]): Validated[E, B]

  def map[C](f: B => C): Check[E, A, C] =
    Map(this, f)
}

object Check {
  def apply[E, A](p: Predicate[E, A]): Check[E, A, A] =
    PureCheck(p)
}

case class PureCheck[E, A](p: Predicate[E, A]) extends Check[E, A, A] {
  override def apply(value: A)(implicit ev: Semigroup[E]): Validated[E, A] =
    p(value)
}

case class Map[E, A, B, C](c: Check[E, A, B], f: B => C) extends Check[E, A, C] {
  override def apply(value: A)(implicit ev: Semigroup[E]): Validated[E, C] =
    c(value).map(f)
}
