package scalograf
package model.panels.status_history

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class ShowValue(val value: String) extends StringEnumEntry

object ShowValue extends StringEnum[ShowValue] with StringCirceEnum[ShowValue] {
  val values = findValues

  case object Auto extends ShowValue("auto")
  case object Always extends ShowValue("always")
  case object Never extends ShowValue("never")
}
