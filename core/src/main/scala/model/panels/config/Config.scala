package scalograf
package model.panels.config

import model.panels.config.FieldConfig.CustomFieldConfig

import io.circe.Decoder.Result
import io.circe.syntax._
import io.circe.{Codec, HCursor, JsonObject}

case class Config[T <: CustomFieldConfig](
    defaults: FieldConfig[T] = FieldConfig[T](),
    overrides: List[Override[T]] = List.empty
)

object Config {
  implicit def codec[T <: CustomFieldConfig](implicit codecT: Codec.AsObject[T]): Codec[Config[T]] =
    new Codec.AsObject[Config[T]](
    ) {
      override def apply(c: HCursor): Result[Config[T]] =
        for {
          defaults <- c.downField("defaults").as[Option[FieldConfig[T]]]
          overrides <- c.downField("overrides").as[Option[List[Override[T]]]]
        } yield Config[T](
          defaults = defaults.getOrElse(FieldConfig[T]()),
          overrides = overrides.getOrElse(List.empty)
        )

      override def encodeObject(fc: Config[T]): JsonObject =
        JsonObject(
          "defaults" -> fc.defaults.asJson,
          "overrides" -> fc.overrides.asJson
        )

    }
}
