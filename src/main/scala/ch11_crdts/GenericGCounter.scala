package ch11_crdts

import cats.Monoid
import cats.instances.all._
import cats.syntax.all._

final case class GenericGCounter[A](counters: Map[String, A]) {
  def increment(machine: String, amount: A)(implicit ev: Monoid[A]): GenericGCounter[A] = GenericGCounter {
    counters.updated(machine, counters.getOrElse(machine, ev.empty) |+| amount)
  }

  def get(implicit ev: Monoid[A]): A =
    counters.values.toList.combineAll

  def merge(that: GenericGCounter[A])(implicit ev: BoundedSemiLattice[A]): GenericGCounter[A] = GenericGCounter {
    that.counters ++ (for {
      (machine, amount) <- counters
    } yield (machine, ev.combine(amount, that.counters.getOrElse(machine, ev.empty))))
  }
}
