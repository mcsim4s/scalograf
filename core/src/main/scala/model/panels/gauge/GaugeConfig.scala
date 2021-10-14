package scalograf
package model.panels.gauge

import model.panels.config.FieldConfig.CustomFieldConfig

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class GaugeConfig(
) extends CustomFieldConfig

object GaugeConfig {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[GaugeConfig]
}
