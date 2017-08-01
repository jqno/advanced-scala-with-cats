package ch09_pygmy_hadoop

import cats.Monoid
import cats.syntax.all._

object PygmyHadoop {

  def foldMap[A, B: Monoid](as: Vector[A])(f: A => B): B =
    as.map(f).reduce(_ |+| _)
}
