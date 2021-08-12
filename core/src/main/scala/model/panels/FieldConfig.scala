package scalograf
package model.panels

import model.enums.{ColorMode, ThresholdMode}
import model.panels.FieldConfig._
import model.{Color, Mappings}

import io.circe.Decoder.Result
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._
import io.circe.syntax._
import io.circe.{Codec, HCursor, JsonObject}

case class FieldConfig[T <: CustomFieldConfig](
    defaults: Defaults[T] = Defaults[T](),
    overrides: List[Override] = List.empty
)

object FieldConfig {

  trait CustomFieldConfig

  case class DefaultsColor(
      mode: ColorMode = ColorMode.PaletteClassic
  )

  case class ThresholdStep(
      color: Color,
      value: Double
  )

  case class Thresholds(
      mode: ThresholdMode = ThresholdMode.Absolute,
      steps: List[ThresholdStep] = List.empty
  )

  case class Defaults[T <: CustomFieldConfig](
      color: DefaultsColor = DefaultsColor(),
      mappings: List[Mappings] = List.empty,
      decimals: Option[Int] = None,
      thresholds: Thresholds = Thresholds(),
      unit: Option[String] = None,
      min: Option[Double] = None,
      max: Option[Double] = None,
      custom: Option[T] = None
  )

  implicit val codecConfig = Configuration.default.withDefaults

  implicit val codecDefaultsColor = deriveConfiguredCodec[DefaultsColor]
  implicit val codecThresholdStep = deriveConfiguredCodec[ThresholdStep]
  implicit val codecThresholds = deriveConfiguredCodec[Thresholds]

  implicit def codecDefaults[T <: CustomFieldConfig](implicit codecT: Codec[T]): Codec[Defaults[T]] =
    new Codec.AsObject[Defaults[T]](
    ) {
      override def apply(c: HCursor): Result[Defaults[T]] =
        for {
          color <- c.downField("color").as[Option[DefaultsColor]]
          mappings <- c.downField("mappings").as[Option[List[Mappings]]]
          decimals <- c.downField("decimals").as[Option[Int]]
          thresholds <- c.downField("thresholds").as[Option[Thresholds]]
          unit <- c.downField("unit").as[Option[String]]
          min <- c.downField("min").as[Option[Double]]
          max <- c.downField("max").as[Option[Double]]
          custom <- c.downField("custom").as[Option[T]]
        } yield Defaults[T](
          color = color.getOrElse(DefaultsColor()),
          mappings = mappings.getOrElse(List.empty),
          decimals = decimals,
          thresholds = thresholds.getOrElse(Thresholds()),
          unit = unit,
          min = min,
          max = max,
          custom = custom
        )

      override def encodeObject(d: Defaults[T]): JsonObject =
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

  implicit def codec[T <: CustomFieldConfig](implicit codecT: Codec[T]): Codec[FieldConfig[T]] =
    new Codec.AsObject[FieldConfig[T]](
    ) {
      override def apply(c: HCursor): Result[FieldConfig[T]] =
        for {
          defaults <- c.downField("defaults").as[Option[Defaults[T]]]
          overrides <- c.downField("overrides").as[Option[List[Override]]]
        } yield FieldConfig[T](
          defaults = defaults.getOrElse(Defaults[T]()),
          overrides = overrides.getOrElse(List.empty)
        )

      override def encodeObject(fc: FieldConfig[T]): JsonObject =
        JsonObject(
          "defaults" -> fc.defaults.asJson,
          "overrides" -> fc.overrides.asJson
        )

    }
}
