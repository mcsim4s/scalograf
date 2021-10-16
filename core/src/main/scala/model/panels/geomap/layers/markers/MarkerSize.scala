package scalograf
package model.panels.geomap.layers.markers

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class MarkerSize(
    field: String = "Value",
    fixed: Int = 5,
    max: Int = 50,
    min: Int = 10
)

object MarkerSize {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[MarkerSize] = deriveConfiguredCodec[MarkerSize]
}
