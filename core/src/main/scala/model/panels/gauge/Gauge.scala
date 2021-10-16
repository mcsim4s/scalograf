package scalograf
package model.panels.gauge

import model.Target
import model.link.Link
import model.panels.Panel
import model.panels.config.Config
import model.transformations.Transformation
import model.time._

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import io.circe.{Codec, JsonObject}

import scala.concurrent.duration.FiniteDuration

case class Gauge(
    cacheTimeout: Option[FiniteDuration] = None,
    fieldConfig: Config[GaugeConfig] = Config[GaugeConfig](),
    hideTimeOverride: Boolean = false,
    links: List[Link] = List.empty,
    options: Options = Options(),
    pluginVersion: Option[String] = None,
    targets: List[Target] = List.empty,
    transformations: List[Transformation] = List.empty
) extends Panel.Type {
  override def `type`: String = "gauge"

  override def asJson: JsonObject = Gauge.codec.encodeObject(this)
}

object Gauge {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Gauge] = deriveConfiguredCodec[Gauge]
}
