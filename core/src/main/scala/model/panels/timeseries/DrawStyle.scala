package scalograf
package model.panels.timeseries

import enumeratum.EnumEntry.Lowercase
import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class DrawStyle(val value: String) extends StringEnumEntry with Lowercase

object DrawStyle extends StringEnum[DrawStyle] with StringCirceEnum[DrawStyle] {
  val values = findValues

  case object Line extends DrawStyle("line")
  case object Bars extends DrawStyle("bars")
  case object Points extends DrawStyle("points")
}
