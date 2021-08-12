package scalograf
package model.panels.timeseries

import enumeratum.EnumEntry.Lowercase
import enumeratum.values.StringEnumEntry
import enumeratum.{CirceEnum, Enum}

sealed abstract class ShowPoints(val value: String) extends StringEnumEntry with Lowercase

object ShowPoints extends Enum[ShowPoints] with CirceEnum[ShowPoints] {
  val values = findValues

  case object Auto extends ShowPoints("auto")
  case object Always extends ShowPoints("always")
  case object Never extends ShowPoints("never")
}
