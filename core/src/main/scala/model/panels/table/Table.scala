package scalograf
package model.panels.table

import model.panels._
import model.transformations.Transformation
import model.{Link, Target, Time}

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Table(
    datasource: Option[String] = None,
    description: Option[String] = None,
    fieldConfig: FieldConfig[TableConfig] = FieldConfig[TableConfig](),
    fontSize: Option[String] = None, //ToDo units
    gridPos: GridPosition,
    height: Option[String] = None, //ToDo unit
    hideTimeOverride: Boolean = false,
    id: Option[Int] = None,
    interval: Option[String] = None,
    links: List[Link] = List.empty,
    maxDataPoints: Option[Int] = None,
    options: Options = Options(),
    pageSize: Option[Int] = None,
    pluginVersion: Option[String] = None,
    repeat: Option[String] = None, //ToDo repeat model
    repeatDirection: Option[String] = None,
    scroll: Boolean = false,
    showHeader: Boolean = false,
    sort: Option[SortBy] = None,
    targets: List[Target] = List.empty,
    timeFrom: Option[String] = None, //ToDo time model
    timeShift: Option[Time] = None,
    title: Option[String] = None,
    transformations: List[Transformation] = List.empty,
    transparent: Boolean = false
) extends Panel {
  override def `type`: String = "table"

  override def asJson: JsonObject = Table.codec.encodeObject(this)
}

object Table {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Table] = deriveConfiguredCodec[Table]
}
