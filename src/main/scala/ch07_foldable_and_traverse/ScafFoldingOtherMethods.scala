package ch07_foldable_and_traverse

object ScafFoldingOtherMethods {

  def map[A, B](as: List[A])(f: A => B): List[B] = ???
  def flatMap[A, B](as: List[A])(f: A => List[B]): List[B] = ???
  def filter[A](as: List[A])(p: A => Boolean): List[A] = ???
  def sum(ints: List[Int]): Int = ???
}
