package scalograf
package model.panels.singlestat

import model.panels.config.FieldConfig.CustomFieldConfig

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class SingleStatConfig(
) extends CustomFieldConfig

object SingleStatConfig {
  implicit val codecConfig: Configuration = Configuration.default
  implicit val codec: Codec.AsObject[SingleStatConfig] = deriveConfiguredCodec[SingleStatConfig]
}
