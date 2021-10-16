package scalograf
package model.panels.geomap

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import scalograf.model.panels.geomap.layers.GeoMapLayer

case class Options(
    basemap: BaseMap = BaseMap(),
    controls: GeoMapControls = GeoMapControls(),
    layers: List[GeoMapLayer] = List.empty,
    view: GeoMapView = GeoMapView()
)

object Options {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Options] = deriveConfiguredCodec[Options]
}
