package scalograf
package model.panels

import FieldConfig._
import model.{Color, Mappings}

import io.circe.JsonObject
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

case class FieldConfig(
    defaults: Defaults,
    overrides: List[Override] = List.empty
)

object FieldConfig {

  case class DefaultsColor(
      mode: String = "thresholds" //ToDo enum
  )

  case class ThresholdStep(
      color: Color,
      value: Double
  )

  case class Thresholds(
      mode: String = "absolute", //ToDo enum
      steps: List[ThresholdStep] = List.empty
  )

  case class Defaults(
      color: DefaultsColor = DefaultsColor(),
      mappings: List[Mappings] = List.empty,
      decimals: Option[Int] = None,
      thresholds: Thresholds = Thresholds(),
      unit: Option[String] = None,
      min: Option[Double] = None,
      max: Option[Double] = None,
      custom: JsonObject = JsonObject()
  )

  implicit val codecConfig = Configuration.default.withDefaults

  implicit val defaultsColorCodec = deriveConfiguredCodec[DefaultsColor]
  implicit val thresholdStepCodec = deriveConfiguredCodec[ThresholdStep]
  implicit val thresholdscodec = deriveConfiguredCodec[Thresholds]
  implicit val defaultscodec = deriveConfiguredCodec[Defaults]
  implicit val codec = deriveConfiguredCodec[FieldConfig]
}
