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

final case class Cat(name: String, age: Int, color: String)

object PrintableInstances {
  implicit val printableString = new Printable[String] {
    override def format(a: String): String = a
  }
  implicit val printableInt = new Printable[Int] {
    override def format(a: Int): String = a.toString
  }
  implicit val printableCat = new Printable[Cat] {
    override def format(a: Cat): String = {
      val name = Printable.format(a.name)
      val age = Printable.format(a.age)
      val color = Printable.format(a.color)
      s"$name is a $age year old $color cat."
    }
  }
}

object PrintableSyntax {
  implicit class PrintOps[A](a: A) {
    def format(implicit printable: Printable[A]): String =
      Printable.format(a)

    def print(implicit printable: Printable[A]): Unit =
      a.format
  }
}
