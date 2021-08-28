package scalograf
package model.alert.condition.query

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class EvaluationType(val value: String) extends StringEnumEntry

object EvaluationType extends StringEnum[EvaluationType] with StringCirceEnum[EvaluationType] {
  val values = findValues

  case object IsAbove extends EvaluationType("gt")
  case object IsBelow extends EvaluationType("lt")
  case object IsOutsideRange extends EvaluationType("outside_range")
  case object IsWithinRange extends EvaluationType("within_range")
  case object HasNoValue extends EvaluationType("no_value")
}
