package ch10_data_validation

import cats.data.NonEmptyList

object Predicates {
  type Errors = NonEmptyList[String]

  def error(s: String): Errors =
    NonEmptyList(s, Nil)

  def longerThan(n: Int): Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must be longer than $n characters"),
      _.length > n
    )

  val alphanumeric: Predicate[Errors, String] =
    Predicate.lift(
      error("Must be all alphanumeric characters"),
      s => s.forall(_.isLetterOrDigit)
    )

  def contains(c: Char): Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must contain the character $c"),
      _.contains(c)
    )

  def containsOnce(c: Char): Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must contain the character $c exactly once"),
      _.filter(_ == c).length == 1
    )
}
