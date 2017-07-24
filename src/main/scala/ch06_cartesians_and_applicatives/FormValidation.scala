package ch06_cartesians_and_applicatives

object FormValidation {

  def getValue(map: Map[String, String], fieldName: String): String =
    map(fieldName)
}
