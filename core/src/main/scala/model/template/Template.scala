package scalograf
package model.template

import model.template.query.QueryTemplate

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._
import io.circe.syntax._
import io.circe.{Decoder, DecodingFailure, Encoder, JsonObject}
import scalograf.model.template.adhoc.AdhocFilterTemplate

case class Template(
    allValue: Option[String] = None,
    current: Option[TemplateOption] = None,
    options: List[TemplateOption] = List.empty,
    description: Option[String] = None,
    hide: TemplateHide = TemplateHide.None,
    includeAll: Boolean = false,
    label: Option[String] = None,
    multi: Boolean = false,
    name: String,
    tags: List[String] = List.empty,
    typed: Template.Type
)

object Template {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val errorCodec = deriveConfiguredCodec[Error]

  case class Error()

  trait Type {
    def `type`: String
    def asJson: JsonObject
  }

  implicit val encoder: Encoder.AsObject[Template] =
    Encoder.AsObject.instance { template =>
      JsonObject(
        "allValue" -> template.allValue.asJson,
        "current" -> template.current.asJson,
        "description" -> template.description.asJson,
        "hide" -> template.hide.asJson,
        "includeAll" -> template.includeAll.asJson,
        "label" -> template.label.asJson,
        "multi" -> template.multi.asJson,
        "name" -> template.name.asJson,
        "options" -> template.options.asJson,
        "tags" -> template.tags.asJson,
        "type" -> template.typed.`type`.asJson
      ).deepMerge(template.typed.asJson)
    }

  implicit val decoder: Decoder[Template] =
    Decoder.instance[Template] { c =>
      c.downField("type")
        .as[String]
        .flatMap[DecodingFailure, Type] {
          case "query"      => c.as[QueryTemplate]
          case "datasource" => c.as[DatasourceTemplate]
          case "custom"     => c.as[CustomTemplate]
          case "textbox"    => c.as[TextBoxTemplate]
          case "constant"   => c.as[ConstantTemplate]
          case "interval"   => c.as[IntervalTemplate]
          case "adhoc"      => c.as[AdhocFilterTemplate]
          case other        => Left(DecodingFailure(s"Unknown template type '$other'", c.history))
        }
        .flatMap { typed =>
          for {
            allValue <- c.downField("allValue").as[Option[String]]
            current <- c.downField("current").as[Option[TemplateOption]]
            description <- c.downField("description").as[Option[String]]
            hide <- c.downField("hide").as[TemplateHide]
            includeAll <- c.downField("includeAll").as[Option[Boolean]]
            label <- c.downField("label").as[Option[String]]
            multi <- c.downField("multi").as[Option[Boolean]]
            name <- c.downField("name").as[String]
            options <- c.downField("options").as[Option[List[TemplateOption]]]
            tags <- c.downField("tags").as[Option[List[String]]]
          } yield Template(
            allValue = allValue,
            current = current,
            description = description,
            hide = hide,
            includeAll = includeAll.getOrElse(false),
            label = label,
            multi = multi.getOrElse(false),
            name = name,
            options = options.getOrElse(List.empty),
            tags = tags.getOrElse(List.empty),
            typed = typed
          )
        }
    }
}
