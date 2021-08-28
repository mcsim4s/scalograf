package scalograf
package model.alert.condition.query

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class ReducerType(val value: String) extends StringEnumEntry

object ReducerType extends StringEnum[ReducerType] with StringCirceEnum[ReducerType] {
  val values = findValues

  case object Average extends ReducerType("avg")
  case object Count extends ReducerType("count")
  case object CountNotNull extends ReducerType("count_not_null")
  case object Diff extends ReducerType("diff")
  case object DiffAbs extends ReducerType("diff_abs")
  case object Last extends ReducerType("last")
  case object Max extends ReducerType("max")
  case object Median extends ReducerType("median")
  case object Min extends ReducerType("min")
  case object PercentDiff extends ReducerType("percent_diff")
  case object PercentDiffAbs extends ReducerType("percent_diff_abs")
  case object Sum extends ReducerType("sum")
}
