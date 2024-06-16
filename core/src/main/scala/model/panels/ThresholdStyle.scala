package scalograf
package model.panels

import model.panels.ThresholdStyle._

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class ThresholdStyle(mode: ThresholdStyleMode = ThresholdStyleMode.Off)

object ThresholdStyle {
  implicit val config: Configuration = Configuration.default.withDefaults

  import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

  sealed abstract class ThresholdStyleMode(val value: String) extends StringEnumEntry

  object ThresholdStyleMode extends StringEnum[ThresholdStyleMode] with StringCirceEnum[ThresholdStyleMode] {
    val values = findValues

    case object Off extends ThresholdStyleMode("off")
    case object Line extends ThresholdStyleMode("line")
    case object AsFilledRegion extends ThresholdStyleMode("area")
    case object AsFiiledRegionAndLine extends ThresholdStyleMode("line+area")
  }
  implicit val codec: Codec.AsObject[ThresholdStyle] = deriveConfiguredCodec[ThresholdStyle]
}
