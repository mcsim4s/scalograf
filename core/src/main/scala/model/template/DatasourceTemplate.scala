package scalograf
package model.template

import io.circe.JsonObject
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class DatasourceTemplate(
    query: String = "",
    regex: Option[String] = None
) extends Template.Type {
  override def `type`: String = "datasource"

  override def asJson: JsonObject = DatasourceTemplate.codec.encodeObject(this)
}

object DatasourceTemplate {

  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[DatasourceTemplate]
}
