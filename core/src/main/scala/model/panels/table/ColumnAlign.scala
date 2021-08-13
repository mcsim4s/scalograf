package scalograf
package model.panels.table

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class ColumnAlign(val value: String) extends StringEnumEntry

object ColumnAlign extends StringEnum[ColumnAlign] with StringCirceEnum[ColumnAlign] {
  val values = findValues

  case object Auto extends ColumnAlign("auto")
  case object Left extends ColumnAlign("left")
  case object Center extends ColumnAlign("center")
  case object Right extends ColumnAlign("right")
}
