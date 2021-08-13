package scalograf
package model.panels.table

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class ColumnDisplayMode(val value: String) extends StringEnumEntry

object ColumnDisplayMode extends StringEnum[ColumnDisplayMode] with StringCirceEnum[ColumnDisplayMode] {
  val values = findValues

  case object Auto extends ColumnDisplayMode("auto")
  case object ColorText extends ColumnDisplayMode("color-text")
  case object ColorBackgroundGradient extends ColumnDisplayMode("color-background")
  case object ColorBackgroundSolid extends ColumnDisplayMode("color-background-solid")
  case object GradientGauge extends ColumnDisplayMode("gradient-gauge")
  case object LcdGauge extends ColumnDisplayMode("lcd-gauge")
  case object BasicGauge extends ColumnDisplayMode("basic")
  case object JsonView extends ColumnDisplayMode("json-view")
}
