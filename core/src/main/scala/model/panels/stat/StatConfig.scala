package scalograf
package model.panels.stat

import model.panels.config.FieldConfig.CustomFieldConfig

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class StatConfig(
) extends CustomFieldConfig

object StatConfig {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[StatConfig]
}
