package scalograf
package model.enums

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class ColorMode(val value: String) extends StringEnumEntry

object ColorMode extends StringEnum[ColorMode] with StringCirceEnum[ColorMode] {
  val values = findValues

  case object PaletteClassic extends ColorMode("palette-classic")
  case object Thresholds extends ColorMode("thresholds")
  case object Fixed extends ColorMode("fixed")
  case object ContinuousBluePurple extends ColorMode("continuous-BlPu")
  case object ContinuousBlueYellowRed extends ColorMode("continuous-BlYlRd")
}
