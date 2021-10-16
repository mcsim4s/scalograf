package scalograf
package model.panels.geomap.layers

import model.panels.geomap.layers.markers.MarkersLayer

import io.circe.Decoder.Result
import io.circe.generic.extras.Configuration
import io.circe.syntax._
import io.circe.{Codec, DecodingFailure, HCursor, JsonObject}

trait GeoMapLayer {
  def `type`: String
  def asJson: JsonObject
}

object GeoMapLayer {

  implicit val config: Configuration = Configuration.default.withDefaults

  implicit val codec: Codec.AsObject[GeoMapLayer] = new Codec.AsObject[GeoMapLayer] {
    override def encodeObject(a: GeoMapLayer): JsonObject = a.asJson.add("type", a.`type`.asJson)

    override def apply(c: HCursor): Result[GeoMapLayer] =
      c.downField("type")
        .as[String]
        .flatMap {
          case "markers" => c.as[MarkersLayer]
          case other     => Left(DecodingFailure(s"Unknown GeoMapLayer type '$other'", c.history))
        }
  }
}
