package scalograf
package model.panels.timeseries

import model.Target
import model.alert.Alert
import model.panels._
import model.panels.config.Config
import model.time._
import model.transformations.Transformation

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

import scala.concurrent.duration.FiniteDuration

case class TimeSeries(
    alert: Option[Alert] = None,
    fieldConfig: Config[TimeSeriesConfig] = Config[TimeSeriesConfig](),
    interval: Option[String] = None,
    maxDataPoints: Option[Int] = None,
    options: Options = Options(),
    pluginVersion: Option[String] = None,
    targets: List[Target] = List.empty,
    timeFrom: Option[FiniteDuration] = None,
    timeShift: Option[FiniteDuration] = None,
    transformations: List[Transformation] = List.empty,
    repeat: Option[String] = None
) extends Panel.Type {
  override def `type`: String = "timeseries"

  override def asJson: JsonObject = TimeSeries.codec.encodeObject(this)
}

object TimeSeries {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[TimeSeries] = deriveConfiguredCodec[TimeSeries]
}
