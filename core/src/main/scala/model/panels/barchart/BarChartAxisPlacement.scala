package scalograf
package model.panels.barchart

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class BarChartAxisPlacement(val value: String) extends StringEnumEntry

object BarChartAxisPlacement extends StringEnum[BarChartAxisPlacement] with StringCirceEnum[BarChartAxisPlacement] {
  val values = findValues

  case object Auto extends BarChartAxisPlacement("auto")
  case object Left extends BarChartAxisPlacement("left")
  case object Right extends BarChartAxisPlacement("right")
  case object Hidden extends BarChartAxisPlacement("hidden")
}
