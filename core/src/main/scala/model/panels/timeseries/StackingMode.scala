package scalograf
package model.panels.timeseries

import enumeratum.EnumEntry.Lowercase
import enumeratum.values.StringEnumEntry
import enumeratum.{CirceEnum, Enum}

sealed abstract class StackingMode(val value: String) extends StringEnumEntry with Lowercase

object StackingMode extends Enum[StackingMode] with CirceEnum[StackingMode] {
  val values = findValues

  case object None extends StackingMode("none")
  case object Normal extends StackingMode("normal")
}
