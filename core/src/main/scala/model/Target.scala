package scalograf
package model

import model.enums.TargetFormat

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

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
  implicit val codec: Codec.AsObject[Target] = deriveConfiguredCodec[Target]
}
