package scalograf
package model.styles

import io.circe.JsonObject
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class DateStyle(
    alias: String, //ToDo enum
    dateFormat: Option[String] = None,
    pattern: String
) extends Style {
  override def `type`: String = "date"

  override def asJson: JsonObject = DateStyle.codec.encodeObject(this)
}

object DateStyle {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[DateStyle]
}
