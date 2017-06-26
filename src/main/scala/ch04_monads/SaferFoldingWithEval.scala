package ch04_monads

import cats.Eval

object SaferFoldingWithEval {

  def foldRight1[A, B](as: List[A], acc: B)(fn: (A, B) => B): B = as match {
    case head :: tail => fn(head, foldRight1(tail, acc)(fn))
    case Nil => acc
  }

  def foldRight2[A, B](as: List[A], acc: B)(fn: (A, B) => B): B = {
    def loop(ls: List[A], lacc: B): Eval[B] = ls match {
      case head :: tail => Eval.defer(loop(tail, lacc)).map(fr => fn(head, fr))
      case Nil => Eval.now(lacc)
    }

    loop(as, acc).value
  }
}
