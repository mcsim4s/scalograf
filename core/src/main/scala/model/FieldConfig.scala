package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._
import FieldConfig._

case class FieldConfig(
    defaults: Defaults
//                        overrides: List[String] // ToDo
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
//      mappings: List[String] = List.empty,
      decimals: Option[Int] = None,
      thresholds: Thresholds = Thresholds()
//      unit: String //ToDo enum
  )

  implicit val codecConfig = Configuration.default.withDefaults

  implicit val defaultsColorCodec = deriveConfiguredCodec[DefaultsColor]
  implicit val thresholdStepCodec = deriveConfiguredCodec[ThresholdStep]
  implicit val thresholdscodec = deriveConfiguredCodec[Thresholds]
  implicit val defaultscodec = deriveConfiguredCodec[Defaults]
  implicit val codec = deriveConfiguredCodec[FieldConfig]
}
