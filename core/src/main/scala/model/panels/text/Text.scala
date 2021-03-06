package scalograf
package model.panels.text

import model.panels.{GridPosition, Panel}

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Text(
    options: Options = Options(),
    pluginVersion: Option[String] = None,
    transparent: Boolean = false
) extends Panel.Type {
  override def `type`: String = "text"

  override def asJson: JsonObject = Text.codec.encodeObject(this)

}

object Text {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Text] = deriveConfiguredCodec[Text]
}
