package scalograf
package model.styles

import model.Color

import io.circe.JsonObject
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class HiddenStyle(
    alias: String, //ToDo enum
    colorMode: Option[String] = None, //ToDo enum
    colors: List[Color] = List.empty,
    dateFormat: Option[String] = None,
    decimals: Int = 2,
    pattern: String,
    thresholds: List[String] = List.empty, //ToDo threshold model
    unit: String //ToDo units
) extends Style {
  override def `type`: String = "hidden"

  override def asJson: JsonObject = HiddenStyle.codec.encodeObject(this)
}

object HiddenStyle {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[HiddenStyle]
}
