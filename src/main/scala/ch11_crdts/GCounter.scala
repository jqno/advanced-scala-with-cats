package ch11_crdts

final case class GCounter(counters: Map[String, Int]) {
  def increment(machine: String, amount: Int): GCounter =
    GCounter(counters.updated(machine, counters.getOrElse(machine, 0) + amount))

  def get: Int =
    counters.values.sum

  def merge(that: GCounter): GCounter = GCounter {
    that.counters ++ (for {
      (machine, amount) <- counters
    } yield (machine, amount max that.counters.getOrElse(machine, 0)))
  }
}
