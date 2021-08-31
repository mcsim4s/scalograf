package scalograf
package model.panels.text

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class TextMode(val value: String) extends StringEnumEntry

object TextMode extends StringEnum[TextMode] with StringCirceEnum[TextMode] {
  val values = findValues

  case object Markdown extends TextMode("markdown")
  case object Html extends TextMode("html")
}
