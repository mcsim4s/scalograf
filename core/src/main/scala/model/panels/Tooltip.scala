package scalograf
package model.panels

import model.panels.Tooltip.Mode

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Tooltip(mode: Mode = Mode.Single)

object Tooltip {
  sealed abstract class Mode(val value: String) extends StringEnumEntry

  object Mode extends StringEnum[Mode] with StringCirceEnum[Mode] {
    val values = findValues

    case object Single extends Mode("single")
    case object Hidden extends Mode("hidden")
  }

  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Tooltip]
}
