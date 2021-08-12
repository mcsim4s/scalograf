package scalograf
package model.panels.timeseries

import enumeratum.EnumEntry.Lowercase
import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class ShowPoints(val value: String) extends StringEnumEntry with Lowercase

object ShowPoints extends StringEnum[ShowPoints] with StringCirceEnum[ShowPoints] {
  val values = findValues

  case object Auto extends ShowPoints("auto")
  case object Always extends ShowPoints("always")
  case object Never extends ShowPoints("never")
}
