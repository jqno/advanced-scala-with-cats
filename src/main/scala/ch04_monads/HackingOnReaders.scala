package ch04_monads

import cats.data.Reader

object HackingOnReaders {
  case class Db(usernames: Map[Int, String], passwords: Map[String, String])

  type DbReader[A] = Reader[Db, A]

  def findUsername(userId: Int): DbReader[Option[String]] =
    Reader(_.usernames.get(userId))

  def checkPassword(username: String, password: String): DbReader[Boolean] =
    Reader(_.passwords.get(username).contains(password))

  def checkLogin(userId: Int, password: String): DbReader[Boolean] =
    for {
      username <- findUsername(userId)
      valid <- username match {
        case Some(u) => checkPassword(u, password)
        case None => Reader((_: Db) => false)
      }
    } yield valid
}
