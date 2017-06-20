package ch03_functors

object contramap {

  trait Printable[A] {
    self =>

    def format(value: A): String
    def contramap[B](f: B => A): Printable[B] = new Printable[B] {
      override def format(value: B): String = self.format(f(value))
    }
  }

  def format[A](value: A)(implicit p: Printable[A]): String =
    p.format(value)

  implicit val stringPrintable = new Printable[String] {
    override def format(value: String): String = "\"" + value + "\""
  }
  implicit val booleanPrintable = new Printable[Boolean] {
    override def format(value: Boolean): String =
      if (value) "yes" else "nope"
  }

  case class Box[A](value: A)

  implicit def boxPrintable[A](implicit p: Printable[A]): Printable[Box[A]] =
    p.contramap(_.value)
}