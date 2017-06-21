package ch03_functors

import scala.util.Try

object imap {
  selfImap =>

  trait Codec[A] {
    self =>

    def encode(value: A): String
    def decode(value: String): Option[A]

    def imap[B](dec: A => B, enc: B => A): Codec[B] = new Codec[B] {
      override def encode(value: B): String = self.encode(enc(value))
      override def decode(value: String): Option[B] = self.decode(value).map(dec)
    }
  }

  def encode[A](value: A)(implicit c: Codec[A]): String = c.encode(value)
  def decode[A](value: String)(implicit c: Codec[A]): Option[A] = c.decode(value)

  implicit val intCodec: Codec[Int] = new Codec[Int] {
    override def encode(value: Int): String = value.toString
    override def decode(value: String): Option[Int] = Try(value.toInt).toOption
  }

  implicit def boxCodec[A](implicit c: Codec[A]): Codec[Box[A]] =
    c.imap(Box.apply, _.value)
}
