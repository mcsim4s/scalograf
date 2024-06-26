package scalograf
package model.panels.map

import model.panels.Panel
import model.time._
import model.transformations.Transformation
import model.{Color, Target}

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

import scala.concurrent.duration.FiniteDuration

case class WorldMapPanel(
    circleMaxSize: String,
    circleMinSize: String,
    colors: List[Color] = List.empty,
    decimals: Option[String] = None,
    esMetric: String = "Count", //ToDo enum
    hideEmpty: Boolean = false,
    hideZero: Boolean = false,
    initialZoom: String = "2",
    interval: Option[String] = None,
    locationData: String = "countries", //ToDo enum
    mapCenter: String, //ToDo geopoint
    mapCenterLatitude: Double,
    mapCenterLongitude: Double,
    maxDataPoints: Int,
    mouseWheelZoom: Boolean = true,
    pluginVersion: String,
    showLegend: Boolean = true,
    stickyLabels: Boolean = true,
    tableQueryOptions: Option[TableQueryOptions] = None,
    targets: List[Target] = List.empty,
    thresholds: Option[String] = None,
    timeFrom: Option[FiniteDuration] = None, //ToDo time model
    timeShift: Option[FiniteDuration] = None,
    transformations: List[Transformation] = List.empty,
    unitPlural: Option[String] = None,
    unitSingle: Option[String] = None,
    valueName: Option[String] = None
) extends Panel.Type {
  override def `type`: String = "grafana-worldmap-panel"

  override def asJson: JsonObject = WorldMapPanel.codec.encodeObject(this)
}

object WorldMapPanel {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[WorldMapPanel] = deriveConfiguredCodec[WorldMapPanel]
}
