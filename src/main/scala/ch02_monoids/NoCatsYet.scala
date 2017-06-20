package ch02_monoids

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


  def associativeLaw[A](x: A, y: A, z: A)(implicit sg: Semigroup[A]): Boolean =
    sg.combine(x, sg.combine(y, z)) == sg.combine(sg.combine(x, y), z)

  def identityLaw[A](x: A)(implicit m: Monoid[A]): Boolean =
    (m.combine(x, m.empty) == x) && (m.combine(m.empty, x) == x)


  val booleans = List(true, false)
  val sets: List[Set[Int]] = List(Set.empty[Int], Set(1, 2, 3), Set(1, 1), Set(-1, 1))

  def checkIdentity[A](generator: List[A])(monoid: Monoid[A]): Boolean = {
    val results = for {
      a <- generator
    } yield identityLaw(a)(monoid)

    results.forall(identity)
  }

  def checkAssociativity[A](generator: List[A])(semigroup: Semigroup[A]): Boolean = {
    val results = for {
      a <- generator
      b <- generator
      c <- generator
    } yield associativeLaw(a, b, c)(semigroup)

    results.forall(identity)
  }
}
