package scalograf
package model.panels.gauge

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Options(
    reduceOptions: ReduceOptions = ReduceOptions(),
    showThresholdLabels: Boolean = false,
    showThresholdMarkers: Boolean = false,
    text: TextOptions = TextOptions()
)

object Options {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Options]
}
