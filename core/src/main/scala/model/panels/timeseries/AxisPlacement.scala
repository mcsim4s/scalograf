package scalograf
package model.panels.timeseries

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class AxisPlacement(val value: String) extends StringEnumEntry

object AxisPlacement extends StringEnum[AxisPlacement] with StringCirceEnum[AxisPlacement] {
  val values = findValues

  case object Auto extends AxisPlacement("auto")
  case object Left extends AxisPlacement("left")
  case object Right extends AxisPlacement("right")
  case object Hidden extends AxisPlacement("hidden")
}
