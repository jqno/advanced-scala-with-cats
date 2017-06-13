package chapter01

import cats.Eq
import cats.instances.all._
import cats.syntax.eq._

object EqInstances {
  implicit val eqCat = Eq.instance[Cat] { (cat1, cat2) =>
    cat1.name === cat2.name && cat1.age === cat2.age && cat1.color === cat2.color
  }
}
