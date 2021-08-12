package scalograf
package model.panels.timeseries

import model.panels.timeseries.Options._

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Options(
    legend: Legend = Legend(),
    tooltip: Tooltip = Tooltip()
)

object Options {
  case class Legend(calcs: List[String] = List.empty, displayMode: String = "list", placement: String = "bottom")
  case class Tooltip(mode: String = "single")

  implicit val config = Configuration.default.withDefaults
  implicit val codecLegend = deriveConfiguredCodec[Legend]
  implicit val codecTooltip = deriveConfiguredCodec[Tooltip]
  implicit val codec = deriveConfiguredCodec[Options]
}
