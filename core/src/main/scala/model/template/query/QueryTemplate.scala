package scalograf
package model.template.query

import model._
import model.template.Template

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}
import io.circe.{Decoder, Encoder, JsonObject}

case class QueryTemplate(
    definition: String = "",
    regex: Option[String],
    refresh: TemplateRefresh = TemplateRefresh.OnLoad,
    sort: ValuesSort = ValuesSort.Disabled,
    datasource: String //ToDo datasource model????
) extends Template.Type {
  override def `type`: String = "query"

  override def asJson: JsonObject = QueryTemplate.encoder.encodeObject(this)
}

object QueryTemplate {

  implicit val codecConfig: Configuration = Configuration.default.withDefaults

  implicit val encoder: Encoder.AsObject[QueryTemplate] =
    deriveConfiguredEncoder[QueryTemplate]
      .mapJsonObject(changeEncodeName("definition", "query"))

  implicit val decoder: Decoder[QueryTemplate] =
    deriveConfiguredDecoder[QueryTemplate]
      .prepare(changeDecodeName("query", "definition"))
}
