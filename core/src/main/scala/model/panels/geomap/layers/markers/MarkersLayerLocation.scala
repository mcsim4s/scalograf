package scalograf
package model.panels.geomap.layers.markers

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class MarkersLayerLocation(
    lookup: String = "Metric", //ToDo ???
    mode: String = "lookup"
)

object MarkersLayerLocation {

  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[MarkersLayerLocation] = deriveConfiguredCodec[MarkersLayerLocation]

}
