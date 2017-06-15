package chapter02

import NoCatsYet._

object SetInstances {
  def appendMonoid[A] = new Monoid[Set[A]] {
    override def empty: Set[A] = Set.empty
    override def combine(x: Set[A], y: Set[A]): Set[A] = x ++ y
  }

  def unionMonoid[A] = new Monoid[Set[A]] {
    override def empty: Set[A] = Set.empty
    override def combine(x: Set[A], y: Set[A]): Set[A] = x | y
  }

  def intersectSemigroup[A] = new Semigroup[Set[A]] {
    override def combine(x: Set[A], y: Set[A]): Set[A] = x & y
  }
}
