package scalograf
package model.panels.timeseries

import enumeratum.EnumEntry.Lowercase
import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class GradientMode(val value: String) extends StringEnumEntry with Lowercase

object GradientMode extends StringEnum[GradientMode] with StringCirceEnum[GradientMode] {
  val values = findValues

  case object None extends GradientMode("none")
  case object Opacity extends GradientMode("opacity")
  case object Hue extends GradientMode("hue")
}
