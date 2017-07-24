package ch06_cartesians_and_applicatives

object FormValidation {

  type FormData = Map[String, String]
  type ErrorsOr[A] = Either[List[String], A]

  def getValue(map: Map[String, String], fieldName: String): ErrorsOr[String] =
    map.get(fieldName).toRight(List(s"Field name $fieldName not specified"))
}
