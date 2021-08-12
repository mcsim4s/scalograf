package scalograf
package model.panels.graph

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Threshold(
    colorMode: ThresholdColorMode = ThresholdColorMode.Critical,
    fill: Boolean = false,
    line: Boolean = false,
    op: String // ToDo operation model
)

object Threshold {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Threshold]
}
