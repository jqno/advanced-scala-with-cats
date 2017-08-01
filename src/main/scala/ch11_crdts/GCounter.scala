package ch11_crdts

final case class GCounter(counters: Map[String, Int]) {
  def increment(machine: String, amount: Int): GCounter = ???

  def get: Int = ???

  def merge(that: GCounter): GCounter = ???
}
