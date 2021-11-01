package scalograf
package model.panels.config

import model.Color
import model.enums.ThresholdMode
import model.link.Link
import model.panels.config.FieldConfig._

import io.circe.Decoder.Result
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import io.circe.syntax._
import io.circe.{Codec, HCursor, JsonObject}

case class FieldConfig[T <: CustomFieldConfig](
    color: Option[ColorConfig] = None,
    custom: Option[T] = None,
    decimals: Option[Int] = None,
    links: Option[List[Link]] = None,
    mappings: Option[List[Mappings]] = None,
    max: Option[Double] = None,
    min: Option[Double] = None,
    nullValueMode: Option[String] = None, //ToDo enum
    thresholds: Option[Thresholds] = None,
    unit: Option[String] = None
)

object FieldConfig {
  trait CustomFieldConfig

  case class ThresholdStep(
      color: Color,
      value: Option[Double] = None
  )

  case class Thresholds(
      mode: ThresholdMode = ThresholdMode.Absolute,
      steps: List[ThresholdStep] = List.empty
  )

  implicit val codecConfig: Configuration = Configuration.default
  implicit val codecThresholdStep: Codec.AsObject[ThresholdStep] = deriveConfiguredCodec[ThresholdStep]
  implicit val codecThresholds: Codec.AsObject[Thresholds] = deriveConfiguredCodec[Thresholds]

  implicit def codec[T <: CustomFieldConfig](implicit codecT: Codec[T]): Codec.AsObject[FieldConfig[T]] =
    new Codec.AsObject[FieldConfig[T]](
    ) {
      override def apply(c: HCursor): Result[FieldConfig[T]] =
        for {
          color <- c.downField("color").as[Option[ColorConfig]]
          custom <- c.downField("custom").as[Option[T]]
          nullValueMode <- c.downField("nullValueMode").as[Option[String]]
          decimals <- c.downField("decimals").as[Option[Int]]
          mappings <- c.downField("mappings").as[Option[List[Mappings]]]
          max <- c.downField("max").as[Option[Double]]
          min <- c.downField("min").as[Option[Double]]
          thresholds <- c.downField("thresholds").as[Option[Thresholds]]
          unit <- c.downField("unit").as[Option[String]]
          links <- c.downField("links").as[Option[List[Link]]]
        } yield FieldConfig[T](
          color = color,
          custom = custom,
          nullValueMode = nullValueMode,
          decimals = decimals,
          mappings = mappings,
          max = max,
          min = min,
          thresholds = thresholds,
          unit = unit,
          links = links
        )

      override def encodeObject(d: FieldConfig[T]): JsonObject =
        JsonObject(
          "color" -> d.color.asJson,
          "custom" -> d.custom.asJson,
          "decimals" -> d.decimals.asJson,
          "mappings" -> d.mappings.asJson,
          "max" -> d.max.asJson,
          "min" -> d.min.asJson,
          "nullValueMode" -> d.nullValueMode.asJson,
          "thresholds" -> d.thresholds.asJson,
          "unit" -> d.unit.asJson,
          "links" -> d.links.asJson
        )
    }
}
