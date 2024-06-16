package scalograf
package model.panels.barchart

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class BarChartGradientMode(val value: String) extends StringEnumEntry

object BarChartGradientMode extends StringEnum[BarChartGradientMode] with StringCirceEnum[BarChartGradientMode] {
  val values = findValues

  case object None extends BarChartGradientMode("none")
  case object Opacity extends BarChartGradientMode("opacity")
  case object Hue extends BarChartGradientMode("hue")
  case object Scheme extends BarChartGradientMode("scheme")
}
