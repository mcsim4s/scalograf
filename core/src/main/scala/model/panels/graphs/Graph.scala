package scalograf
package model.panels.graphs

import model.panels.{GridPosition, Panel}

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Graph(
    //    cacheTimeout: Option[Time] = None,
    datasource: Option[String] = None,
    description: Option[String] = None,
    //    fieldConfig: Option[FieldConfig] = None,
    gridPos: GridPosition,
    //    hideTimeOverride: Boolean = false,
    id: Int,
    //    interval: Option[String] = None,
    //    links: List[Link] = List.empty,
    //    maxDataPoints: Int = 300,
    //    options: Options = Options(),
    //    pluginVersion: Option[String] = None, //ToDo ???
    //    targets: List[Target] = List.empty,
    //    timeFrom: Option[String] = None, //ToDo time model
    //    timeShift: Option[Time] = None,
    title: Option[String] = None
    //    transformations: List[Transformation] = List.empty
) extends Panel {
  override def `type`: String = "graph"

  override def asJson: JsonObject = Graph.codec.encodeObject(this)
}

object Graph {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Graph] = deriveConfiguredCodec[Graph]
}
