package scalograf
package model.panels.stat

import model.panels._
import model._

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Stat(
    cacheTimeout: Option[Time] = None,
    datasource: Option[String] = None,
    description: Option[String] = None,
    fieldConfig: FieldConfig[StatConfig] = FieldConfig(),
    gridPos: GridPosition,
    hideTimeOverride: Boolean = false,
    id: Option[Int] = None,
    interval: Option[String] = None,
    links: List[Link] = List.empty,
    maxDataPoints: Int = 300,
    options: Options = Options(),
    pluginVersion: Option[String] = None, //ToDo ???
    targets: List[Target] = List.empty,
    timeFrom: Option[String] = None, //ToDo time model
    timeShift: Option[Time] = None,
    title: Option[String] = None,
    transformations: List[Transformation] = List.empty
) extends Panel {
  override def `type`: String = "stat"

  override def asJson: JsonObject = Stat.codec.encodeObject(this)
}

object Stat {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Stat] = deriveConfiguredCodec[Stat]
}
