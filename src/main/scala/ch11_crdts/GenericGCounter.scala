package ch11_crdts

import cats.Monoid
import cats.instances.all._
import cats.syntax.all._

final case class GenericGCounter[A: BoundedSemiLattice](counters: Map[String, A]) {
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
