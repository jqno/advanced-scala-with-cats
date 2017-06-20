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
    override def format(value: String): String = value
  }
}