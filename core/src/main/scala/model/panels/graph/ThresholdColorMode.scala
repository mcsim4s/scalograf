package scalograf
package model.panels.graph

import enumeratum.EnumEntry.Lowercase
import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class ThresholdColorMode(val value: String) extends StringEnumEntry with Lowercase

object ThresholdColorMode extends StringEnum[ThresholdColorMode] with StringCirceEnum[ThresholdColorMode] {
  val values = findValues

  case object Critical extends ThresholdColorMode("critical")
}
