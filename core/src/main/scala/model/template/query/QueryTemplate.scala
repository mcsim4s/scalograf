package scalograf
package model.template.query

import model.template.Template

import io.circe.JsonObject
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class QueryTemplate(
    definition: String = "",
    regex: Option[String],
    refresh: TemplateRefresh = TemplateRefresh.OnLoad,
    sort: ValuesSort = ValuesSort.Disabled,
    datasource: String //ToDo datasource model????
) extends Template.Type {
  override def `type`: String = "query"

  override def asJson: JsonObject = QueryTemplate.codec.encodeObject(this)
}

object QueryTemplate {

  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[QueryTemplate]
}
