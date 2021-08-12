package scalograf
package model.panels.timeseries

import enumeratum.EnumEntry.Lowercase
import enumeratum.values.StringEnumEntry
import enumeratum.{CirceEnum, Enum}

sealed abstract class ThresholdStyleMode(val value: String) extends StringEnumEntry with Lowercase

object ThresholdStyleMode extends Enum[ThresholdStyleMode] with CirceEnum[ThresholdStyleMode] {
  val values = findValues

  case object Off extends ThresholdStyleMode("off")
  case object Line extends ThresholdStyleMode("line")
  case object AsFilledRegion extends ThresholdStyleMode("area")
  case object AsFiiledRegionAndLine extends ThresholdStyleMode("line+area")
}
