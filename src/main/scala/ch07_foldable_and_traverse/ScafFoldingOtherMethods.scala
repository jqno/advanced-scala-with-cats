package ch07_foldable_and_traverse

object ScafFoldingOtherMethods {

  def map[A, B](as: List[A])(f: A => B): List[B] =
    as.foldRight(List.empty[B])((a, acc) => f(a) :: acc)

  def flatMap[A, B](as: List[A])(f: A => List[B]): List[B] =
    as.foldRight(List.empty[B])((a, acc) => f(a) ::: acc)

  def filter[A](as: List[A])(p: A => Boolean): List[A] =
    as.foldRight(List.empty[A])((a, acc) => if (p(a)) a :: acc else acc)

  def sum(ints: List[Int]): Int =
    ints.foldRight(0)((i, acc) => i + acc)
}
