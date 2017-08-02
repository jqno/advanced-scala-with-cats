package ch11_crdts

final case class GenericGCounter(counters: Map[String, Int]) {
  def increment(machine: String, amount: Int): GenericGCounter =
    GenericGCounter(counters.updated(machine, counters.getOrElse(machine, 0) + amount))

  def get: Int =
    counters.values.sum

  def merge(that: GenericGCounter): GenericGCounter = GenericGCounter {
    that.counters ++ (for {
      (machine, amount) <- counters
    } yield (machine, amount max that.counters.getOrElse(machine, 0)))
  }
}
