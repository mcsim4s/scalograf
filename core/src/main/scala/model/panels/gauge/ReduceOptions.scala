package scalograf
package model.panels.gauge

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class ReduceOptions(
    calcs: List[ReduceCalculation] = List.empty,
    fields: GaugeFields = GaugeFields.Numeric,
    values: Boolean = false
)

object ReduceOptions {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[ReduceOptions]
}
