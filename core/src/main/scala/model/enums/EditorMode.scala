package scalograf
package model.enums

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class EditorMode(val value: String) extends StringEnumEntry

object EditorMode extends StringEnum[EditorMode] with StringCirceEnum[EditorMode] {
  val values = findValues

  case object Code extends EditorMode("code")
  case object Builder extends EditorMode("builder")
}
