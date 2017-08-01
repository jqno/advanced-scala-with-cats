package ch09_pygmy_hadoop

import cats.instances.all._
import ch09_pygmy_hadoop.PygmyHadoop.foldMap
import org.scalatest.{FlatSpec, Matchers}

class PygmyHadoopTest extends FlatSpec with Matchers {

  behavior of "foldMap"

  it should "calculate a sum using identity" in {
    foldMap(Vector(1, 2, 3))(identity) should be (6)
  }

  it should "map to a String and concatenate" in {
    foldMap(Vector(1, 2, 3))(_.toString + "! ") should be ("1! 2! 3! ")
  }

  it should "map over a String to produce a String" in {
    foldMap("Hello world!".toVector)(_.toString.toUpperCase) should be ("HELLO WORLD!")
  }
}
