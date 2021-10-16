package scalograf
package model

import model.enums.TargetFormat

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}
import io.circe.{Decoder, Encoder}

case class Target(
    exemplar: Boolean = false,
    expr: String,
    format: TargetFormat = TargetFormat.TimeSeries,
    hide: Boolean = false,
    instant: Boolean = false,
    interval: Option[String] = None,
    intervalFactor: Option[Int] = None,
    legendFormat: String = "",
    maxLines: Option[Int] = None,
    metric: Option[String] = None,
    range: Boolean = false,
    refId: String,
    step: Option[Double] = None
)

object Target {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val encoder: Encoder[Target] =
    deriveConfiguredEncoder[Target]
      .mapJsonObject(changeEncodeName("expr", "expression"))

  implicit val decoder: Decoder[Target] =
    deriveConfiguredDecoder[Target]
      .prepare(changeDecodeName("expression", "expr"))
}
