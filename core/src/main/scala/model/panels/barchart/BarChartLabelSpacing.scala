package scalograf
package model.panels.barchart

import enumeratum.values.{IntCirceEnum, IntEnum, IntEnumEntry}

sealed abstract class BarChartLabelSpacing(val value: Int) extends IntEnumEntry

object BarChartLabelSpacing extends IntEnum[BarChartLabelSpacing] with IntCirceEnum[BarChartLabelSpacing] {
  val values = findValues
  
  case object LargeRight extends BarChartLabelSpacing(-300)
  case object MediumRight extends BarChartLabelSpacing(-200)
  case object SmallRight extends BarChartLabelSpacing(-100)
  case object None extends BarChartLabelSpacing(0)
  case object Small extends BarChartLabelSpacing(100)
  case object Medium extends BarChartLabelSpacing(200)
  case object Large extends BarChartLabelSpacing(300)
}
