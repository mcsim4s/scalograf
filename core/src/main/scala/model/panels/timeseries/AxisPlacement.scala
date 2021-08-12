package scalograf
package model.panels.timeseries

import enumeratum.EnumEntry.Lowercase
import enumeratum.values.StringEnumEntry
import enumeratum.{CirceEnum, Enum}

sealed abstract class AxisPlacement(val value: String) extends StringEnumEntry with Lowercase

object AxisPlacement extends Enum[AxisPlacement] with CirceEnum[AxisPlacement] {
  val values = findValues

  case object Auto extends AxisPlacement("auto")
  case object Left extends AxisPlacement("left")
  case object Right extends AxisPlacement("right")
  case object Hidden extends AxisPlacement("hidden")
}
