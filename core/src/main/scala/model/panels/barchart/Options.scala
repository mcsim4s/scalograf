package scalograf
package model.panels.barchart

import model.panels.timeseries.StackingMode
import model.panels.{Legend, Tooltip}

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Options(
    legend: Legend = Legend(),
    tooltip: Tooltip = Tooltip(),
    barRadius: Double = 0, // 0..0.5
    barWidth: Double = 0.95, // 0..1
    fullHighlight: Boolean = false,
    groupWidth: Double = 0.8, // 0..1
    orientation: BarChartOrientation = BarChartOrientation.Auto,
    showValue: BarChartShowValue = BarChartShowValue.Auto,
    stacking: StackingMode = StackingMode.None,
    xField: Option[String] = None,
    colorByField: Option[String] = None,
    xTickLabelMaxLength: Option[Int] = None,
    xTickLabelRotation: Option[Int] = None, // -90..90
    xTickLabelSpacing: BarChartLabelSpacing = BarChartLabelSpacing.None
)

object Options {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Options] = deriveConfiguredCodec[Options]
}
