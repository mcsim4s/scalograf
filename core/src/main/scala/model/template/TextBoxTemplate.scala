package scalograf
package model.template

import io.circe.JsonObject
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class TextBoxTemplate(
    query: String = ""
) extends Template.Type {
  override def `type`: String = "textbox"

  override def asJson: JsonObject = TextBoxTemplate.codec.encodeObject(this)
}

object TextBoxTemplate {

  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[TextBoxTemplate]
}
