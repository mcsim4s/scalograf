package scalograf
package model.enums

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class Orientation(val value: String) extends StringEnumEntry

object Orientation extends StringEnum[Orientation] with StringCirceEnum[Orientation] {
  val values: IndexedSeq[Orientation] = findValues

  case object Horizontal extends Orientation("horizontal")
  case object Vertical extends Orientation("vertical")
}
