package ch04_monads

import ch03_functors.{Branch, Leaf, Tree}
import ch04_monads.BranchingOutFurther.treeMonad
import org.scalatest.{FlatSpec, Matchers}

class BranchingOutFurtherTest extends FlatSpec with Matchers {

  def duplicate[A](a: A): Tree[A] =
    Branch(Leaf(a), Leaf(a))

  val someLeaf: Tree[Int] = Leaf(1)
  val someBranch: Tree[Int] = Branch(Leaf(1), Branch(Leaf(2), Leaf(3)))


  behavior of "TreeMonad"

  it should "work on Leaf" in {
    val expected = Branch(Leaf(1), Leaf(1))
    val actual = treeMonad.flatMap(someLeaf)(duplicate)
    actual should be (expected)
  }

  it should "work on Branch" in {
    val expected = Branch(Branch(Leaf(1), Leaf(1)), Branch(Branch(Leaf(2), Leaf(2)), Branch(Leaf(3), Leaf(3))))
    val actual = treeMonad.flatMap(someBranch)(duplicate)
    actual should be (expected)
  }

  it should "have Functor-like behaviour" in {
    val expected = Branch(Leaf(2), Branch(Leaf(3), Leaf(4)))
    val actual = treeMonad.map(someBranch)(_ + 1)
    actual should be (expected)
  }

  it should "support for-comprehensions when the monad is in the implicit scope" in {
    import cats.syntax.flatMap._
    import cats.syntax.functor._
    implicit val tm = treeMonad
    val t = for {
      a <- someLeaf
      b <- someBranch
    } yield a + b
    t should be (Branch(Leaf(2), Branch(Leaf(3), Leaf(4))))
  }
}
