package scalograf
package model.panels.graph

import model._

import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}

case class Tooltip(
    msResolution: Boolean = false,
    shared: Boolean = false,
    sort: Int,
    valueType: String //ToDo enum
)

object Tooltip {

  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val encoder: Encoder.AsObject[Tooltip] = deriveConfiguredEncoder[Tooltip]
    .mapJsonObject(changeEncodeName("valueType", "value_type"))

  implicit val decoder: Decoder[Tooltip] = deriveConfiguredDecoder[Tooltip]
    .prepare(changeDecodeName("value_type", "valueType"))
}
