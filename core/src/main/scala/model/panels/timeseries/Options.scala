package scalograf
package model.panels.timeseries

import model.panels.{Legend, Tooltip}

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Options(
    legend: Legend = Legend(),
    tooltip: Tooltip = Tooltip()
)

object Options {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Options]
}
