package ch06_cartesians_and_applicatives

import scala.util.Try

object FormValidation {

  type FormData = Map[String, String]
  type ErrorsOr[A] = Either[List[String], A]

  def getValue(map: FormData, fieldName: String): ErrorsOr[String] =
    map.get(fieldName).toRight(List(s"Field name $fieldName not specified"))

  def parseInt(map: FormData, fieldName: String): ErrorsOr[Int] =
    getValue(map, fieldName).right.flatMap { v =>
      Try(v.toInt).toOption.toRight(List(s"Value $v in $fieldName was not an int"))
    }
}
