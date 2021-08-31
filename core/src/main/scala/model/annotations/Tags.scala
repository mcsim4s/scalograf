package scalograf
package model.annotations

import model.Color

import io.circe.JsonObject
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Tags(
    datasource: String,
    enable: Boolean = true,
    expr: String,
    hide: Boolean = false,
    iconColor: Color,
    limit: Long = 100,
    name: String,
    showIn: Long = 0,
    step: Option[String] = None, // ToDo time
    tagKeys: String,
    tags: List[String] = List.empty,
    titleFormat: Option[String] = None
) extends Annotation {

  override def `type`: String = "tags"

  override def asJson: JsonObject = Tags.codec.encodeObject(this)
}

object Tags {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Tags]
}
