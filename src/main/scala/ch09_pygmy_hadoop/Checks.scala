package ch09_pygmy_hadoop

import cats.instances.all._
import cats.syntax.all._
import ch09_pygmy_hadoop.Predicates._

object Checks {

  val usernameCheck: Check[Errors, String, String] = Check {
    longerThan(3) and alphanumeric
  }

  val emailCheck: Check[Errors, String, String] =
    Check(contains('@')).flatMap { s =>
      val (left :: right :: Nil) = s.split('@').toList
      val l = Check(longerThan(0))(left)
      val r = Check(longerThan(2) and contains('.'))(right)
      Check(_ => l |+| r)
    }
}
