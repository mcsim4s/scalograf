package scalograf
package model.alert.condition.query

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class OperationType(val value: String) extends StringEnumEntry

object OperationType extends StringEnum[OperationType] with StringCirceEnum[OperationType] {
  val values = findValues

  case object And extends OperationType("and")
  case object Or extends OperationType("or")
}
