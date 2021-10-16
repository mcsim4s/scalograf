package scalograf
package model.panels.graph

import model.panels.Panel
import model.time._
import model.{Color, Target}
import model.alert.Alert

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import scalograf.model.link.Link
import scalograf.model.panels.config.Config

import scala.concurrent.duration.FiniteDuration

case class Graph(
    alert: Option[Alert] = None,
    aliasColors: Map[String, Color] = Map.empty,
    bars: Boolean = false,
    dashLength: Option[Int] = None,
    dashes: Boolean = false,
    decimals: Option[Int] = None, //ToDo ???
    editable: Boolean = true,
    error: Boolean = false,
    fieldConfig: Config[GraphConfig] = Config[GraphConfig](),
    fill: Int, //ToDo ???
    fillGradient: Option[Int] = None,
    grid: Grid = Grid(),
    height: Option[String] = None, //ToDo units
    hiddenSeries: Boolean = false,
    isNew: Boolean = false, //ToDo ???
    legend: Legend = Legend(),
    lines: Boolean = false,
    linewidth: Int = 1,
    links: List[Link] = List.empty,
    maxPerRow: Option[Int] = None,
    nullPointMode: String, //ToDo enum
    options: Options = Options(),
    percentage: Boolean = false,
    pluginVersion: Option[String] = None,
    pointradius: Option[Double] = None,
    points: Boolean = false,
    renderer: Renderer = Renderer.Flot,
    repeat: Option[FiniteDuration] = None,
    repeatDirection: Option[String] = None,
    seriesOverrides: List[SeriesOverrides] = List.empty,
    spaceLength: Int,
    stack: Boolean = false,
    steppedLine: Boolean = false,
    targets: List[Target] = List.empty,
    thresholds: List[Threshold] = List.empty,
    timeFrom: Option[FiniteDuration] = None,
    timeRegions: List[TimeRegion] = List.empty,
    timeShift: Option[FiniteDuration] = None,
    tooltip: Option[Tooltip] = None,
    transparent: Boolean = false,
    xaxis: XAxis = XAxis(),
    yaxes: List[Axes] = List.empty,
    yaxis: YAxis = YAxis()
) extends Panel.Type {
  override def `type`: String = "graph"

  override def asJson: JsonObject = Graph.codec.encodeObject(this)
}

object Graph {
  implicit val codecConfig: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Graph] = deriveConfiguredCodec[Graph]
}
