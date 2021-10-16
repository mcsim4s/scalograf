package scalograf
package model.panels.geomap

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class GeoMapView(
    id: String = "zero",
    lat: Double = 0,
    lon: Double = 0,
    zoom: Int = 2
)

object GeoMapView {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[GeoMapView] = deriveConfiguredCodec[GeoMapView]
}
