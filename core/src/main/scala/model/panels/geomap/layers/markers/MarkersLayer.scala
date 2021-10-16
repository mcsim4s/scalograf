package scalograf
package model.panels.geomap.layers.markers

import model.panels.geomap.layers.GeoMapLayer

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import io.circe.{Codec, JsonObject}

case class MarkersLayer(
    config: MarkersLayerConfig = MarkersLayerConfig(),
    location: MarkersLayerLocation = MarkersLayerLocation()
) extends GeoMapLayer {
  val `type`: String = "markers"

  override def asJson: JsonObject = MarkersLayer.codec.encodeObject(this)
}

object MarkersLayer {

  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[MarkersLayer] = deriveConfiguredCodec[MarkersLayer]

}
