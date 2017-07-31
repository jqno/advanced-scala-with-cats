package ch09_pygmy_hadoop

import cats.data.NonEmptyList
import ch09_pygmy_hadoop.Checks._
import ch09_pygmy_hadoop.Predicates.Errors
import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.{FlatSpec, Matchers}

class ChecksTest extends FlatSpec with Matchers {

  behavior of "usernameCheck"

  it should "fail if the username is shorter than 4 characters" in {
    usernameCheck("aaa") should beInvalidWithErrors("Must be longer than 3 characters")
  }

  it should "fail if the username contains non-alphanumeric characters" in {
    usernameCheck("aaa$") should beInvalidWithErrors("Must be all alphanumeric characters")
  }

  it should "fail if the username is too short and contains invalid characters" in {
    usernameCheck("a$") should beInvalidWithErrors("Must be longer than 3 characters", "Must be all alphanumeric characters")
  }

  it should "succeed if the username is long enough" in {
    usernameCheck("aaaa").isRight should be (true)
  }

  it should "succeed with all alphanumeric characters" in {
    usernameCheck("qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890").isRight should be (true)
  }


  behavior of "emailCheck"

  it should "fail if the email doesn't contain an @" in {
    emailCheck("emailaddress.com") should beInvalidWithErrors("Must contain the character @")
  }

  it should "fail if the left part is empty" in {
    emailCheck("@address.com") should beInvalidWithErrors("Must be longer than 0 characters")
  }

  it should "fail if the right part doesn't contain a ." in {
    emailCheck("email@addresscom") should beInvalidWithErrors("Must contain the character .")
  }

  it should "fail if the right part is shorter than 3 characters" in {
    emailCheck("email@.c") should beInvalidWithErrors("Must be longer than 2 characters")
  }

  it should "combine multiple error messages" in {
    emailCheck("@com") should beInvalidWithErrors("Must be longer than 0 characters", "Must contain the character .")
  }

  it should "succeed if the email is valid" in {
    emailCheck("email@address.com").isRight should be (true)
  }


  def beInvalidWithErrors(head: String, tail: String*) =
    new InvalidWithErrorsMatcher(head, tail:_*)

  class InvalidWithErrorsMatcher(head: String, tail: String*) extends Matcher[Either[Errors, _]] {
    override def apply(left: Either[Errors, _]): MatchResult = {
      val errors = NonEmptyList(head, tail.toList)
      left match {
        case Right(_) => MatchResult(false, s"$left should have been invalid", "")
        case Left(err) if err == errors => MatchResult(true, "", "")
        case Left(err) => MatchResult(false, s"$err should have been $errors", "")
      }
    }
  }
}
