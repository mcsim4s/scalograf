package scalograf
package model.template.adhoc

import model.template.Template

import io.circe.JsonObject
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class AdhocFilterTemplate(
    filters: List[AdHocFilter] = List.empty,
    datasource: Option[String] = None
) extends Template.Type {
  override def `type`: String = "adhoc"

  override def asJson: JsonObject = AdhocFilterTemplate.codec.encodeObject(this)
}

object AdhocFilterTemplate {

  implicit val codecConfig = Configuration.default
  implicit val codec = deriveConfiguredCodec[AdhocFilterTemplate]
}
