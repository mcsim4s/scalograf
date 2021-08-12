package scalograf
package model.enums

import enumeratum.EnumEntry.Lowercase
import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class ThresholdMode(val value: String) extends StringEnumEntry with Lowercase

object ThresholdMode extends StringEnum[ThresholdMode] with StringCirceEnum[ThresholdMode] {
  val values = findValues

  case object Absolute extends ThresholdMode("absolute")
  case object Percentage extends ThresholdMode("percentage")
}
