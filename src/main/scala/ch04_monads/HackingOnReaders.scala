package ch04_monads

import cats.data.Reader

object HackingOnReaders {
  case class Db(usernames: Map[Int, String], passwords: Map[String, String])

  type DbReader[A] = Reader[Db, A]
}
