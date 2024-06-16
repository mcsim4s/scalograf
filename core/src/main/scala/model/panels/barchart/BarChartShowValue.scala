package scalograf
package model.panels.barchart

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class BarChartShowValue(val value: String) extends StringEnumEntry

object BarChartShowValue extends StringEnum[BarChartShowValue] with StringCirceEnum[BarChartShowValue] {
  val values = findValues

  case object Auto extends BarChartShowValue("auto")
  case object Always extends BarChartShowValue("always")
  case object Never extends BarChartShowValue("never")
}
