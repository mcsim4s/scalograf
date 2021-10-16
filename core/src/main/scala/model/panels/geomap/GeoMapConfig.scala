package scalograf
package model.panels.geomap

import model.panels.config.FieldConfig.CustomFieldConfig

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class GeoMapConfig(
) extends CustomFieldConfig

object GeoMapConfig {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[GeoMapConfig]
}
