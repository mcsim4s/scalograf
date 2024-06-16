package scalograf
package model.panels.barchart

import model.panels.config.FieldConfig.CustomFieldConfig
import model.panels.{Linear, ScaleDistribution, ThresholdStyle}

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class BarChartConfig(
    axisBorderShow: Boolean = false,
    axisCenteredZero: Boolean = false,
    axisColorMode: BarChartAxisColorMode = BarChartAxisColorMode.Text,
    axisLabel: String = "",
    axisPlacement: BarChartAxisPlacement = BarChartAxisPlacement.Auto,
    fillOpacity: Int = 80, // 0..100
    gradientMode: BarChartGradientMode = BarChartGradientMode.None,
    lineWidth: Int = 1,
    scaleDistribution: ScaleDistribution = Linear,
    thresholdsStyle: ThresholdStyle = ThresholdStyle()
) extends CustomFieldConfig

object BarChartConfig {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[BarChartConfig] = deriveConfiguredCodec[BarChartConfig]
}
