package scalograf
package model.panels.config

import model.panels.config.FieldConfig.CustomFieldConfig
import model.panels.config.Override._

import io.circe.Decoder.Result
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import io.circe.syntax._
import io.circe.{Codec, HCursor, Json, JsonObject}

case class Override[T <: CustomFieldConfig](
    matcher: Matcher,
    properties: FieldConfig[T]
)

object Override {
  case class Matcher(id: String, options: String)
  case class Property(id: String, value: Json) {
    def stripCustom: Property = copy(id = this.id.stripPrefix("custom."))
  }

  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codecMatcher: Codec.AsObject[Matcher] = deriveConfiguredCodec[Matcher]
  implicit val codecProperty: Codec.AsObject[Property] = deriveConfiguredCodec[Property]
  implicit def codec[T <: CustomFieldConfig](implicit codecT: Codec.AsObject[T]): Codec.AsObject[Override[T]] =
    new Codec.AsObject[Override[T]] {
      override def encodeObject(o: Override[T]): JsonObject =
        JsonObject(
          "matcher" -> o.matcher.asJson,
          "properties" -> {
            val custom = o.properties.custom
              .map(c =>
                codecT.encodeObject(c).toList.collect {
                  case (key, value) if !value.isNull => Property(s"custom.$key", value)
                }
              )
              .getOrElse(List.empty)

            val common = o.properties.asJsonObject.remove("custom").toList.collect {
              case (key, value) if !value.isNull => Property(key, value)
            }
            (custom ++ common).asJson
          }
        )

      override def apply(c: HCursor): Result[Override[T]] =
        for {
          matcher <- c.downField("matcher").as[Matcher]
          properties <- c.downField("properties").as[List[Property]].flatMap { properties =>
            val (custom, common) = properties.partition(_.id.startsWith("custom."))
            val customObj = custom.map(_.stripCustom).map(p => p.id -> p.value).toMap.asJsonObject
            val commonObj = common.map(p => p.id -> p.value).toMap.asJsonObject
            val withCustom = if (customObj.nonEmpty) commonObj.add("custom", customObj.asJson) else commonObj
            withCustom.asJson.as[FieldConfig[T]]
          }
        } yield Override(
          matcher,
          properties
        )
    }
}
