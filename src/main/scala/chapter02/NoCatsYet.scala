package chapter02

object NoCatsYet {

  trait Semigroup[A] {
    def combine(x: A, y: A): A
  }

  trait Monoid[A] extends Semigroup[A] {
    def empty: A
  }

  object Monoid {
    def apply[A](implicit monoid: Monoid[A]) = monoid
  }


  def associativeLaw[A](x: A, y: A, z: A)(implicit m: Monoid[A]): Boolean =
    m.combine(x, m.combine(y, z)) == m.combine(m.combine(x, y), z)

  def identityLaw[A](x: A)(implicit m: Monoid[A]): Boolean =
    (m.combine(x, m.empty) == x) && (m.combine(m.empty, x) == x)


  val booleans = List(true, false)

  def checkIdentity[A](generator: List[A])(monoid: Monoid[A]): Boolean = {
    val results = for {
      a <- generator
    } yield identityLaw(a)(monoid)

    results.forall(identity)
  }

  def checkAssociativity[A](generator: List[A])(monoid: Monoid[A]): Boolean = {
    val results = for {
      a <- generator
      b <- generator
      c <- generator
    } yield associativeLaw(a, b, c)(monoid)

    results.forall(identity)
  }
}
