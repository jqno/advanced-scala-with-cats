package chapter01

trait Printable[A] {
  def format(a: A): String
}

object Printable {
  def format[A](a: A)(implicit printable: Printable[A]): String =
    printable.format(a)

  def print[A](a: A)(implicit printable: Printable[A]): Unit =
    println(format(a))
}

object PrintableInstances {
  implicit val printableString = new Printable[String] {
    override def format(a: String): String = a
  }
  implicit val printableInt = new Printable[Int] {
    override def format(a: Int): String = a.toString
  }
}
