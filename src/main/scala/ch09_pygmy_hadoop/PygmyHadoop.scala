package ch09_pygmy_hadoop

import cats.Monoid
import cats.syntax.all._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object PygmyHadoop {

  def foldMap[A, B: Monoid](as: Vector[A])(f: A => B): B =
    as.map(f).reduce(_ |+| _)

  def parallelFoldMap[A, B: Monoid](as: Vector[A])(f: A => B): Future[B] = {
    val cpus = Runtime.getRuntime.availableProcessors()
    val work = as.grouped(cpus)
    val batches = work.map { w =>
      Future { foldMap(w)(f) }
    }
    Future.sequence(batches).map(_.reduce(_ |+| _))
  }
}
