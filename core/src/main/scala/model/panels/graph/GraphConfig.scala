package scalograf
package model.panels.graph

import model.panels.config.FieldConfig.CustomFieldConfig

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class GraphConfig(
) extends CustomFieldConfig

object GraphConfig {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[GraphConfig] = deriveConfiguredCodec[GraphConfig]
}
