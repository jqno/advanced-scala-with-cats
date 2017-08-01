package ch09_pygmy_hadoop

import cats.Monoid

object PygmyHadoop {

  def foldMap[A, B: Monoid](as: Vector[A])(f: A => B): B = ???
}
