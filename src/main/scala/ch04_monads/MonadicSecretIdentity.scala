package ch04_monads

object MonadicSecretIdentity {
  type Id[A] = A

  class IdMonad[A] {
    def pure(value: A): Id[A] =
      value

    def flatMap[B](value: A)(f: A => Id[B]): Id[B] =
      f(value)

    def map[B](value: A)(f: A => B): Id[B] =
      f(value)
  }

}
