package scalograf
package model.panels.config

import model.enums.ThresholdMode
import model.panels.config.FieldConfig._
import model.{Color, Mappings}

import io.circe.Decoder.Result
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import io.circe.syntax._
import io.circe.{Codec, HCursor, JsonObject}

case class FieldConfig[T <: CustomFieldConfig](
    color: Option[ColorConfig] = None,
    mappings: Option[List[Mappings]] = None,
    decimals: Option[Int] = None,
    thresholds: Option[Thresholds] = None,
    unit: Option[String] = None,
    min: Option[Double] = None,
    max: Option[Double] = None,
    custom: Option[T] = None
)

object FieldConfig {
  trait CustomFieldConfig

  case class ThresholdStep(
      color: Color,
      value: Double
  )

  case class Thresholds(
      mode: ThresholdMode = ThresholdMode.Absolute,
      steps: List[ThresholdStep] = List.empty
  )

  implicit val codecConfig = Configuration.default
  implicit val codecThresholdStep = deriveConfiguredCodec[ThresholdStep]
  implicit val codecThresholds = deriveConfiguredCodec[Thresholds]

  implicit def codec[T <: CustomFieldConfig](implicit codecT: Codec[T]): Codec.AsObject[FieldConfig[T]] =
    new Codec.AsObject[FieldConfig[T]](
    ) {
      override def apply(c: HCursor): Result[FieldConfig[T]] =
        for {
          color <- c.downField("color").as[Option[ColorConfig]]
          mappings <- c.downField("mappings").as[Option[List[Mappings]]]
          decimals <- c.downField("decimals").as[Option[Int]]
          thresholds <- c.downField("thresholds").as[Option[Thresholds]]
          unit <- c.downField("unit").as[Option[String]]
          min <- c.downField("min").as[Option[Double]]
          max <- c.downField("max").as[Option[Double]]
          custom <- c.downField("custom").as[Option[T]]
        } yield FieldConfig[T](
          color = color,
          mappings = mappings,
          decimals = decimals,
          thresholds = thresholds,
          unit = unit,
          min = min,
          max = max,
          custom = custom
        )

      override def encodeObject(d: FieldConfig[T]): JsonObject =
        JsonObject(
          "color" -> d.color.asJson,
          "mappings" -> d.mappings.asJson,
          "decimals" -> d.decimals.asJson,
          "thresholds" -> d.thresholds.asJson,
          "unit" -> d.unit.asJson,
          "min" -> d.min.asJson,
          "max" -> d.max.asJson,
          "custom" -> d.custom.asJson
        )
    }
}
