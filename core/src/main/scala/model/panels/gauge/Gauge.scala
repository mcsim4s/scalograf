package scalograf
package model.panels.gauge

import model.Target
import model.panels.Panel
import model.transformations.Transformation

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import io.circe.{Codec, JsonObject}
import scalograf.model.panels.config.Config

case class Gauge(
    targets: List[Target] = List.empty,
    transformations: List[Transformation] = List.empty,
    pluginVersion: Option[String] = None,
    options: Options = Options(),
    fieldConfig: Config[GaugeConfig] = Config[GaugeConfig]()
) extends Panel.Type {
  override def `type`: String = "gauge"

  override def asJson: JsonObject = Gauge.codec.encodeObject(this)
}

object Gauge {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Gauge] = deriveConfiguredCodec[Gauge]
}
