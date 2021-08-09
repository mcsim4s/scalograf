package scalograf
package model.panels.graph

import model.panels.{GridPosition, Panel}
import model.{Color, Link, Target, Time}

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Graph(
    aliasColors: Map[String, Color] = Map.empty,
    bars: Boolean = false,
    dashes: Boolean = false,
    dashLength: Int,
    datasource: Option[String] = None,
    decimals: Int, //ToDo ???
    description: Option[String] = None,
    editable: Boolean = true,
    error: Boolean = false,
    fill: Int, //ToDo ???
    grid: Grid = Grid(),
    gridPos: GridPosition,
    height: Option[String] = None, //ToDo units
    id: Int,
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
    repeat: Option[Time] = None,
    repeatDirection: Option[String] = None,
    seriesOverrides: List[String] = List.empty,
    spaceLength: Int,
    stack: Boolean = false,
    steppedLine: Boolean = false,
    targets: List[Target] = List.empty,
    thresholds: List[Threshold] = List.empty,
    timeFrom: Option[Time] = None,
    timeShift: Option[Time] = None,
    title: Option[String] = None,
    tooltip: Option[Tooltip] = None,
    transparent: Boolean = false,
    xaxis: XAxis = XAxis(),
    yaxes: List[Axes] = List.empty,
    yaxis: YAxis = YAxis()
) extends Panel {
  override def `type`: String = "graph"

  override def asJson: JsonObject = Graph.codec.encodeObject(this)
}

object Graph {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Graph] = deriveConfiguredCodec[Graph]
}
