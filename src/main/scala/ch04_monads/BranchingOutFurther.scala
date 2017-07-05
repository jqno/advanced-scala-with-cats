package ch04_monads

import cats.Monad
import ch03_functors.{Branch, Leaf, Tree}

object BranchingOutFurther {
  val treeMonad = new Monad[Tree] {
    override def pure[A](x: A): Tree[A] =
      Leaf(x)

    override def flatMap[A, B](fa: Tree[A])(f: A => Tree[B]): Tree[B] = fa match {
      case Leaf(a) => f(a)
      case Branch(left, right) => Branch(flatMap(left)(f), flatMap(right)(f))
    }

    // I peeked in the book's solutions for this one
    override def tailRecM[A, B](a: A)(f: A => Tree[Either[A, B]]): Tree[B] = f(a) match {
      case Leaf(Left(value)) => tailRecM(value)(f)
      case Leaf(Right(value)) => Leaf(value)
      case Branch(left, right) =>
        Branch(
          flatMap(left) {
            case Left(l) => tailRecM(l)(f)
            case Right(l) => pure(l)
          },
          flatMap(right) {
            case Left(r) => tailRecM(r)(f)
            case Right(r) => pure(r)
          }
        )
    }
  }
}
