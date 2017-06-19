package chapter02

import cats.Monoid
import cats.instances.int._

object SuperAdder {
  def add(items: List[Int]): Int = {
    val monoid = Monoid[Int]
    items.foldRight(monoid.empty)(monoid.combine)
  }
}
