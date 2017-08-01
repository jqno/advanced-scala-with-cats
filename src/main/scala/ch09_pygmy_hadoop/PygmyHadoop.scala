package ch09_pygmy_hadoop

import cats.Monoid
import cats.instances.all._
import cats.syntax.all._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object PygmyHadoop {

  def foldMap[A, B: Monoid](as: Vector[A])(f: A => B): B =
    as.map(f).reduce(_ |+| _)

  def parallelFoldMap[A, B: Monoid](as: Vector[A])(f: A => B): Future[B] = {
    val cpus = Runtime.getRuntime.availableProcessors()
    val groupSize = (1.0 * as.size / cpus).ceil.toInt
    val work = as.grouped(groupSize)
    val batches = work.map { w =>
      Future { foldMap(w)(f) }
    }
    Future.sequence(batches).map(_.reduce(_ |+| _))
  }

  def catsParallelFoldMap[A, B: Monoid](as: Vector[A])(f: A => B): Future[B] = {
    val cpus = Runtime.getRuntime.availableProcessors()
    val groupSize = (1.0 * as.size / cpus).ceil.toInt
    as
      .grouped(groupSize).toVector
      .traverse(w => Future { w.foldMap(f) })
      .map(_.combineAll)
  }
}
