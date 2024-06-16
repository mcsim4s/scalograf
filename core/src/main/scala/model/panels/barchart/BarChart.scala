package scalograf
package model.panels.barchart

import model.panels.Panel

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import model.Target
import model.time._
import model.panels.config.Config
import model.transformations.Transformation

import scala.concurrent.duration.FiniteDuration

case class BarChart(
    fieldConfig: Config[BarChartConfig] = Config[BarChartConfig](),
    interval: Option[FiniteDuration] = None,
    maxDataPoints: Option[Int] = None,
    options: Options = Options(),
    pluginVersion: Option[String] = None,
    targets: List[Target] = List.empty,
    timeFrom: Option[FiniteDuration] = None,
    timeShift: Option[FiniteDuration] = None,
    transformations: List[Transformation] = List.empty
) extends Panel.Type {
  override def `type`: String = "barchart"

  override def asJson: JsonObject = BarChart.codec.encodeObject(this)
}

object BarChart {
  implicit val codecConfig: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[BarChart] = deriveConfiguredCodec[BarChart]
}
