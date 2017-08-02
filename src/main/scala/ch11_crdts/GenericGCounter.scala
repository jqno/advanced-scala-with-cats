package ch11_crdts

import cats.Monoid
import cats.instances.all._
import cats.syntax.all._

final case class GenericGCounter[A: BoundedSemiLattice](counters: Map[String, A]) {

  // The problem: at least for Ints, we need a different monoid for increment & get
  // (it should do addition), and a different one for merge (it should do max).
  // Not sure how to solve this so I'm going to peek in the answers.

  val monoid = implicitly[Monoid[A]]
  val bsl = implicitly[BoundedSemiLattice[A]]

  def increment(machine: String, amount: A): GenericGCounter[A] = GenericGCounter {
    counters.updated(machine, counters.getOrElse(machine, monoid.empty) |+| amount)
  }

  def get: A =
    counters.values.toList.combineAll

  def merge(that: GenericGCounter[A]): GenericGCounter[A] = GenericGCounter {
    that.counters ++ (for {
      (machine, amount) <- counters
    } yield (machine, bsl.combine(amount, that.counters.getOrElse(machine, monoid.empty))))
  }
}
