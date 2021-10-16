package scalograf
package model.panels.gauge

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class ReduceCalculation(val value: String) extends StringEnumEntry

object ReduceCalculation extends StringEnum[ReduceCalculation] with StringCirceEnum[ReduceCalculation] {
  val values: IndexedSeq[ReduceCalculation] = findValues

  case object LastNotNull extends ReduceCalculation("lastNotNull")
  case object Last extends ReduceCalculation("last")
  case object First extends ReduceCalculation("first")
  case object FirstNotNull extends ReduceCalculation("firstNotNull")
  case object Min extends ReduceCalculation("min")
  case object Max extends ReduceCalculation("max")
  case object Mean extends ReduceCalculation("mean")
  case object Sum extends ReduceCalculation("sum")
  case object Count extends ReduceCalculation("count")
  case object Range extends ReduceCalculation("range")
  case object Delta extends ReduceCalculation("delta")
  case object Step extends ReduceCalculation("step")
  case object Diff extends ReduceCalculation("diff")
  case object MinAboveZero extends ReduceCalculation("logmin")
  case object AllIsZero extends ReduceCalculation("allIsZero")
  case object AllIsNull extends ReduceCalculation("allIsNull")
  case object ChangeCount extends ReduceCalculation("changeCount")
  case object DistinctCount extends ReduceCalculation("distinctCount")
  case object DiffPercent extends ReduceCalculation("diffperc")
  case object AllValues extends ReduceCalculation("allValues")
}
