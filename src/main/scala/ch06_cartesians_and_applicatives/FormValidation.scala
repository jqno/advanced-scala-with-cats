package ch06_cartesians_and_applicatives

import scala.util.Try

object FormValidation {

  type FormData = Map[String, String]
  type ErrorsOr[A] = Either[List[String], A]

  def getValue(data: FormData, fieldName: String): ErrorsOr[String] =
    data.get(fieldName).toRight(List(s"Field name $fieldName not specified"))

  def parseInt(data: FormData, fieldName: String): ErrorsOr[Int] =
    getValue(data, fieldName).right.flatMap { v =>
      Try(v.toInt).toOption.toRight(List(s"Value $v in $fieldName was not an int"))
    }

  def nonBlank(value: String): ErrorsOr[String] =
    if (value.trim == "") Left(List("String was blank")) else Right(value)

  def nonNegative(value: Int): ErrorsOr[Int] =
    if (value < 0) Left(List(s"$value was negative")) else Right(value)

  def readName(data: FormData): ErrorsOr[String] =
    getValue(data, "name").right.flatMap(nonBlank)

  def readAge(data: FormData): ErrorsOr[Int] =
    parseInt(data, "age").right.flatMap(nonNegative)
}
