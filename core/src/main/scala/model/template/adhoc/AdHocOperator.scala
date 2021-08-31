package scalograf
package model.template.adhoc

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class AdHocOperator(val value: String) extends StringEnumEntry

object AdHocOperator extends StringEnum[AdHocOperator] with StringCirceEnum[AdHocOperator] {
  val values = findValues

  case object Equals extends AdHocOperator("=")
  case object NotEquals extends AdHocOperator("!=")
  case object GreaterThen extends AdHocOperator(">")
  case object LessThen extends AdHocOperator("<")
  case object Like extends AdHocOperator("=~")
  case object NotLike extends AdHocOperator("!~")
}
