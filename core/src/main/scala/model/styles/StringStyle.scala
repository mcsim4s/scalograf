package scalograf
package model.styles

import model.Color

import io.circe.JsonObject
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class StringStyle(
    alias: String, //ToDo enum
    colorMode: Option[String] = None, //ToDo enum
    colors: List[Color] = List.empty,
    dateFormat: Option[String] = None,
    decimals: Int = 2,
    pattern: String,
    preserveFormat: Boolean = false,
    sanitize: Boolean = false,
    thresholds: List[String] = List.empty, //ToDo threshold model
    unit: String //ToDo units
) extends Style {
  override def `type`: String = "string"

  override def asJson: JsonObject = StringStyle.codec.encodeObject(this)
}

object StringStyle {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[StringStyle]
}
