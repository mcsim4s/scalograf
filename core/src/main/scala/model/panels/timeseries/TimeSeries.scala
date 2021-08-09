package scalograf
package model.panels.timeseries

import model.panels.{FieldConfig, GridPosition, Panel, Transformation}
import model.{Target, Time}

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class TimeSeries(
    datasource: Option[String] = None,
    description: Option[String] = None,
    fieldConfig: Option[FieldConfig] = None,
    gridPos: GridPosition,
    id: Int,
    interval: Option[String] = None,
    maxDataPoints: Int = 300,
    options: Options = Options(),
    pluginVersion: Option[String] = None,
    targets: List[Target] = List.empty,
    timeFrom: Option[String] = None, //ToDo time model
    timeShift: Option[Time] = None,
    title: Option[String] = None,
    transformations: List[Transformation] = List.empty
) extends Panel {
  override def `type`: String = "timeseries"

  override def asJson: JsonObject = TimeSeries.codec.encodeObject(this)
}

object TimeSeries {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[TimeSeries] = deriveConfiguredCodec[TimeSeries]
}
