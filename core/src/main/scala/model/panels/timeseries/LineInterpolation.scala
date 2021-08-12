package scalograf
package model.panels.timeseries

import enumeratum.EnumEntry.LowerCamelcase
import enumeratum.values.StringEnumEntry
import enumeratum.{CirceEnum, Enum}

sealed abstract class LineInterpolation(val value: String) extends StringEnumEntry with LowerCamelcase

object LineInterpolation extends Enum[LineInterpolation] with CirceEnum[LineInterpolation] {
  val values = findValues

  case object Linear extends LineInterpolation("linear")
  case object Smooth extends LineInterpolation("smooth")
  case object StepBefore extends LineInterpolation("stepBefore")
  case object StepAfter extends LineInterpolation("stepAfter")
}
