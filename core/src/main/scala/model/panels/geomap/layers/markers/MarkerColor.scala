package scalograf
package model.panels.geomap.layers.markers

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import scalograf.model.Color

case class MarkerColor(
    fixed: Color = Color.Named("semi-dark-blue")
)

object MarkerColor {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[MarkerColor] = deriveConfiguredCodec[MarkerColor]
}
