package scalograf
package model.panels.table

import model.panels._
import model.panels.config.Config
import model.time._
import model.transformations.Transformation
import model.Target

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import scalograf.model.link.Link

import scala.concurrent.duration.FiniteDuration

case class Table(
    fieldConfig: Config[TableConfig] = Config[TableConfig](),
    fontSize: Option[String] = None, //ToDo units
    height: Option[String] = None, //ToDo unit
    hideTimeOverride: Boolean = false,
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
    timeFrom: Option[FiniteDuration] = None, //ToDo time model
    timeShift: Option[FiniteDuration] = None,
    transformations: List[Transformation] = List.empty,
    transparent: Boolean = false
) extends Panel.Type {
  override def `type`: String = "table"

  override def asJson: JsonObject = Table.codec.encodeObject(this)
}

object Table {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Table] = deriveConfiguredCodec[Table]
}
