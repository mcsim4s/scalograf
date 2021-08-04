package scalograf
package model.panels.table

import model.panels._
import model.{Target, Time}

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Table(
    datasource: Option[String] = None,
    description: Option[String] = None,
    fieldConfig: Option[FieldConfig] = None,
    gridPos: GridPosition,
    hideTimeOverride: Boolean = false,
    id: Int,
    interval: Option[String] = None,
    maxDataPoints: Int = 300,
    options: Options = Options(),
    targets: List[Target] = List.empty,
    timeFrom: Option[String] = None, //ToDo time model
    timeShift: Option[Time] = None,
    title: Option[String] = None,
    transformations: List[Transformation] = List.empty
) extends Panel {
  override def `type`: String = "table"

  override def asJson: JsonObject = Table.codec.encodeObject(this)
}

object Table {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Table] = deriveConfiguredCodec[Table]
}
