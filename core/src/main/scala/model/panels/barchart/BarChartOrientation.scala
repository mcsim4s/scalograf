package scalograf
package model.panels.barchart

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class BarChartOrientation(val value: String) extends StringEnumEntry

object BarChartOrientation extends StringEnum[BarChartOrientation] with StringCirceEnum[BarChartOrientation] {
  val values = findValues

  case object Auto extends BarChartOrientation("auto")
  case object Horizontal extends BarChartOrientation("horizontal")
  case object Vertical extends BarChartOrientation("vertical")
}
