package scalograf
package model.panels.graph

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class Renderer(val value: String) extends StringEnumEntry

object Renderer extends StringEnum[Renderer] with StringCirceEnum[Renderer] {
  val values: IndexedSeq[Renderer] = findValues

  case object Flot extends Renderer("flot")
}
