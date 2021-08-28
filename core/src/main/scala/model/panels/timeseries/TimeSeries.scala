package scalograf
package model.panels.timeseries

import model.Target
import model.panels._
import model.panels.config.Config
import model.time._
import model.transformations.Transformation

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

import scala.concurrent.duration.FiniteDuration

case class TimeSeries(
    fieldConfig: Config[TimeSeriesConfig] = Config[TimeSeriesConfig](),
    interval: Option[String] = None,
    maxDataPoints: Option[Int] = None,
    options: Options = Options(),
    pluginVersion: Option[String] = None,
    targets: List[Target] = List.empty,
    timeFrom: Option[FiniteDuration] = None, //ToDo time model
    timeShift: Option[FiniteDuration] = None,
    transformations: List[Transformation] = List.empty
) extends Panel.Type {
  override def `type`: String = "timeseries"

  override def asJson: JsonObject = TimeSeries.codec.encodeObject(this)
}

object TimeSeries {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[TimeSeries] = deriveConfiguredCodec[TimeSeries]
}
