package scalograf
package model.panels.geomap

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class GeoMapControls(
    showZoom: Boolean = false,
    showDebug: Boolean = false,
    showScale: Boolean = false,
    showAttribution: Boolean = false,
    mouseWheelZoom: Boolean = false
)

object GeoMapControls {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[GeoMapControls] = deriveConfiguredCodec[GeoMapControls]
}
