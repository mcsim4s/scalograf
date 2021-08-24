package scalograf
package model.panels.status_history

import model.panels.config.FieldConfig.CustomFieldConfig

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class StatusHistoryConfig(
    fillOpacity: Int = 90,
    lineWidth: Int = 0
) extends CustomFieldConfig

object StatusHistoryConfig {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[StatusHistoryConfig]
}
