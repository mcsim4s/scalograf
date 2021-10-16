package scalograf
package model.panels.graph

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class TimeRegion(
)

object TimeRegion {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[TimeRegion] = deriveConfiguredCodec[TimeRegion]
}
