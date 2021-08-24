package scalograf
package model.panels.status_history

import model.panels.{Legend, Tooltip}

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Options(
    colWidth: Double = 0.8,
    rowHeight: Double = 0.8,
    showValue: ShowValue = ShowValue.Auto,
    legend: Legend = Legend(),
    tooltip: Tooltip = Tooltip()
)

object Options {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Options]
}
