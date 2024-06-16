package scalograf
package model.panels.barchart

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class BarChartAxisColorMode(val value: String) extends StringEnumEntry

object BarChartAxisColorMode extends StringEnum[BarChartAxisColorMode] with StringCirceEnum[BarChartAxisColorMode] {
  val values = findValues

  case object Text extends BarChartAxisColorMode("text")
  case object Series extends BarChartAxisColorMode("series")
}
