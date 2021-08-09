package scalograf
package model.panels.singlestat

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Gauge(
    maxValue: Double,
    minValue: Double,
    show: Boolean,
    thresholdLabels: Boolean,
    thresholdMarkers: Boolean
)

object Gauge {
  implicit val codecConfig = Configuration.default
  implicit val codec = deriveConfiguredCodec[Gauge]
}
