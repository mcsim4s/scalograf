package scalograf
package model.panels.gauge

import model.enums.Orientation

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Options(
    reduceOptions: ReduceOptions = ReduceOptions(),
    showThresholdLabels: Boolean = false,
    showThresholdMarkers: Boolean = false,
    text: TextOptions = TextOptions(),
    orientation: Option[Orientation] = None
)

object Options {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Options] = deriveConfiguredCodec[Options]
}
