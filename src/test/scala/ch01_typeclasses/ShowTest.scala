package ch01_typeclasses

import cats.instances.all._
import cats.syntax.show._
import ch01_typeclasses.ShowInstances._
import org.scalatest.{FlatSpec, Matchers}

class ShowTest extends FlatSpec with Matchers {

  behavior of "Show"

  it should "format a String" in {
    // Can't use pretty syntax here,
    // because String already has a format method which conflicts.
    "string".show should be ("string")
  }

  it should "format an Int" in {
    42.show should be ("42")
  }

  it should "format a Cat" in {
    val cat = Cat("Sammie", 10, "brown")
    cat.show should be ("Sammie is a 10 year old brown cat.")
  }
}
