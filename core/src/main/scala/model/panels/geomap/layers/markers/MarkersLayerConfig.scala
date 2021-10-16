package scalograf
package model.panels.geomap.layers.markers

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class MarkersLayerConfig(
    size: MarkerSize = MarkerSize(),
    fillOpacity: Double = 0.3,
    shape: String = "circle", //ToDo enum
    color: MarkerColor = MarkerColor(),
    showLegend: Boolean = false
)

object MarkersLayerConfig {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[MarkersLayerConfig] = deriveConfiguredCodec[MarkersLayerConfig]
}
