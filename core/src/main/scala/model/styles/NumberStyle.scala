package scalograf
package model.styles

import model.Color

import io.circe.JsonObject
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class NumberStyle(
    alias: String, //ToDo enum
    colorMode: Option[String] = None, //ToDo enum
    colors: List[Color] = List.empty,
    dateFormat: Option[String] = None,
    decimals: Int = 2,
    link: Boolean = false,
    mappingType: Option[Int] = None, //ToDo enum
    pattern: String,
    thresholds: List[String] = List.empty, //ToDo threshold model
    unit: String //ToDo units
) extends Style {
  override def `type`: String = "number"

  override def asJson: JsonObject = NumberStyle.codec.encodeObject(this)
}

object NumberStyle {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[NumberStyle]
}
