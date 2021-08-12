package scalograf
package model.panels.timeseries

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class StackingMode(val value: String) extends StringEnumEntry

object StackingMode extends StringEnum[StackingMode] with StringCirceEnum[StackingMode] {
  val values = findValues

  case object None extends StackingMode("none")
  case object Normal extends StackingMode("normal")
}
