package scalograf
package model.panels.graph

import model.panels.Panel
import model.time._
import model.{Color, Link, Target}
import model.alert.Alert

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

import scala.concurrent.duration.FiniteDuration

case class Graph(
    alert: Option[Alert] = None,
    aliasColors: Map[String, Color] = Map.empty,
    bars: Boolean = false,
    dashes: Boolean = false,
    dashLength: Option[Int] = None,
    decimals: Option[Int] = None, //ToDo ???
    editable: Boolean = true,
    error: Boolean = false,
    fill: Int, //ToDo ???
    grid: Grid = Grid(),
    height: Option[String] = None, //ToDo units
    isNew: Boolean = false, //ToDo ???
    legend: Legend = Legend(),
    lines: Boolean = false,
    linewidth: Int = 1,
    links: List[Link] = List.empty,
    nullPointMode: String, //ToDo enum
    percentage: Boolean = false,
    pointradius: Option[Double] = None,
    points: Boolean = false,
    renderer: String,
    repeat: Option[FiniteDuration] = None,
    repeatDirection: Option[String] = None,
    seriesOverrides: List[String] = List.empty,
    spaceLength: Int,
    stack: Boolean = false,
    steppedLine: Boolean = false,
    targets: List[Target] = List.empty,
    thresholds: List[Threshold] = List.empty,
    timeFrom: Option[FiniteDuration] = None,
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
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Graph] = deriveConfiguredCodec[Graph]
}
