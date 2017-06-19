package chapter02

import cats.Monoid
import cats.instances.int._

object SuperAdder {
  def add(items: List[Int]): Int = {
    val monoid = Monoid[Int]
    items.foldRight(monoid.empty)(monoid.combine)
  }

  def add2[A](items: List[A])(implicit monoid: Monoid[A]): A = {
    items.foldRight(monoid.empty)(monoid.combine)
  }
}

case class Order(totalCost: Double, quantity: Double)

object Order {
  import cats.syntax.semigroup._
  import cats.instances.double._

  implicit val orderMonoid = new Monoid[Order] {
    override def empty: Order = Order(0.0, 0.0)
    override def combine(x: Order, y: Order): Order =
      Order(x.totalCost |+| y.totalCost, x.quantity |+| y.quantity)
  }
}
