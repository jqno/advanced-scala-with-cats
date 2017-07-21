package ch06_cartesians_and_applicatives

import scala.language.higherKinds
import cats.Monad

object CartesianAppliedToMonad {
  def product[M[_]: Monad, A, B](fa: M[A], fb: M[B]): M[(A, B)] = {
    val m = implicitly[Monad[M]]
    m.flatMap(fa)(a => m.map(fb)(b => (a, b)))
  }
}
