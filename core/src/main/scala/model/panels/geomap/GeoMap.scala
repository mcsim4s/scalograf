package scalograf
package model.panels.geomap

import model.{Target, TimeRange}
import model.panels.Panel
import model.transformations.Transformation

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import io.circe.{Codec, JsonObject}
import model.time._

import scalograf.model.panels.config.Config

import scala.concurrent.duration.FiniteDuration

case class GeoMap(
    targets: List[Target] = List.empty,
    pluginVersion: Option[String] = None,
    transformations: List[Transformation] = List.empty,
    timeShift: Option[FiniteDuration] = None,
    maxDataPoints: Option[Int] = None,
    options: Options = Options(),
    timeFrom: Option[FiniteDuration] = None,
    fieldConfig: Config[GeoMapConfig] = Config[GeoMapConfig](),
    interval: Option[TimeRange] = None
) extends Panel.Type {
  override def `type`: String = "geomap"

  override def asJson: JsonObject = GeoMap.codec.encodeObject(this)
}

object GeoMap {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[GeoMap] = deriveConfiguredCodec[GeoMap]
}
