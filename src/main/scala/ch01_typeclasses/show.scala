package ch01_typeclasses

import cats.Show
import cats.instances.all._
import cats.syntax.show._

object ShowInstances {
  implicit val showCat = new Show[Cat] {
    override def show(f: Cat): String = {
      val name = f.name.show
      val age = f.age.show
      val color = f.color.show
      s"$name is a $age year old $color cat."
    }
  }
}
