package scalograf
package model.panels.gauge

import model.panels.config.FieldConfig.CustomFieldConfig

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class GaugeConfig(
) extends CustomFieldConfig

object GaugeConfig {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[GaugeConfig] = deriveConfiguredCodec[GaugeConfig]
}
