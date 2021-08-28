package scalograf
package model.enums

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class TargetFormat(val value: String) extends StringEnumEntry

object TargetFormat extends StringEnum[TargetFormat] with StringCirceEnum[TargetFormat] {
  val values = findValues

  case object TimeSeries extends TargetFormat("time_series")
  case object Table extends TargetFormat("table")
  case object HeatMap extends TargetFormat("heatmap")
}
