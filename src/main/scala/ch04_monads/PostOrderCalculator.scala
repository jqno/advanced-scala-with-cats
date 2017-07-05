package ch04_monads

import cats.data.State

object PostOrderCalculator {
  type CalcState[A] = State[List[Int], A]

  def evalOne(sym: String): CalcState[Int] = State { s =>
    def op(f: (Int, Int) => Int) = {
      val (x :: y :: tail) = s
      val a = f(x, y)
      (a :: tail, a)
    }

    sym match {
      case "+" => op(_ + _)
      case "*" => op(_ * _)
      case "-" => op(_ - _)
      case "/" => op(_ / _)
      case _ =>
        val i = sym.toInt
        (i :: s, i)
    }
  }

  def evalAll(input: List[String]): CalcState[Int] =
    input.foldLeft(evalOne("0")) { (state, in) =>
      state.flatMap(_ => evalOne(in))
    }
}
