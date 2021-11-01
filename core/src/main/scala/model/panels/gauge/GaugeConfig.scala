package scalograf
package model.panels.gauge

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import model.panels.config.FieldConfig.CustomFieldConfig

case class GaugeConfig(
    noValue: Option[String] = None
) extends CustomFieldConfig

object GaugeConfig {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[GaugeConfig] = deriveConfiguredCodec[GaugeConfig]
}
