package ch11_crdts

import cats.Monoid

trait BoundedSemiLattice[A] extends Monoid[A] {
  def combine(a1: A, a2: A): A
  def empty: A
}

object BoundedSemiLattice {
  implicit val intBoundedSemiLattice = new BoundedSemiLattice[Int] {
    override def combine(a1: Int, a2: Int): Int = a1 max a2
    override def empty: Int = 0
  }

  implicit def setBoundedSemiLattice[E] = new BoundedSemiLattice[Set[E]] {
    override def combine(a1: Set[E], a2: Set[E]): Set[E] = a1 union a2
    override def empty: Set[E] = Set.empty[E]
  }
}
