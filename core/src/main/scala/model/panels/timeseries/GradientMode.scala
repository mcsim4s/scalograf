package scalograf
package model.panels.timeseries

import enumeratum.EnumEntry.Lowercase
import enumeratum.values.StringEnumEntry
import enumeratum.{CirceEnum, Enum}

sealed abstract class GradientMode(val value: String) extends StringEnumEntry with Lowercase

object GradientMode extends Enum[GradientMode] with CirceEnum[GradientMode] {
  val values = findValues

  case object None extends GradientMode("none")
  case object Opacity extends GradientMode("opacity")
  case object Hue extends GradientMode("hue")
}
