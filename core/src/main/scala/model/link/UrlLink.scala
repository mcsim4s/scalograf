package scalograf
package model.link

import io.circe.{Codec, JsonObject}
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class UrlLink(
    url: String,
    tags: List[String] = List.empty,
    icon: Option[String] = Some("external link"), // ToDo enum ?
    targetBlank: Boolean = false, // Link opens in new tab if true
    title: Option[String] = None
) extends Link {
  val `type`: String = "link"

  override def asJson: JsonObject = UrlLink.codec.encodeObject(this)
}

object UrlLink {

  implicit val codecConfig: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[UrlLink] = deriveConfiguredCodec[UrlLink]
}
