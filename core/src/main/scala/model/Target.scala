package scalograf
package model

import model.enums.{EditorMode, TargetFormat}

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}
import io.circe.{Decoder, Encoder}

case class Target(
    exemplar: Boolean = false,
    expr: String,
    format: TargetFormat = TargetFormat.TimeSeries,
    hide: Boolean = false,
    instant: Boolean = false,
    interval: String = "",
    intervalFactor: Option[Int] = None,
    legendFormat: String = "__auto",
    maxLines: Option[Int] = None,
    metric: Option[String] = None,
    range: Boolean = true,
    refId: String,
    step: Option[Double] = None,
    editorMode: EditorMode = EditorMode.Code
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
