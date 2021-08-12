package scalograf
package model.panels.timeseries

import enumeratum.EnumEntry.Lowercase
import enumeratum.values.StringEnumEntry
import enumeratum.{CirceEnum, Enum}

sealed abstract class DrawStyle(val value: String) extends StringEnumEntry with Lowercase

object DrawStyle extends Enum[DrawStyle] with CirceEnum[DrawStyle] {
  val values = findValues

  case object Line extends DrawStyle("line")
  case object Bars extends DrawStyle("bars")
  case object Points extends DrawStyle("points")
}
