package scalograf
package model

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

case class Panel(
    collapsed: Boolean = false,
    id: Long,
    gridPos: GridPosition,
    panels: List[Panel] = List.empty,
    datasource: Option[String] = None, //ToDo ???
    title: Option[String] = None,
    `type`: String, //ToDo enum
    description: Option[String] = None,
    targets: List[Target] = List.empty,
    pluginVersion: Option[String] = None, //ToDo ???
    transformations: List[Transformation] = List.empty,
    timeShift: Option[Time] = None,
    hideTimeOverride: Boolean = false,
    maxDataPoints: Int = 300,
    options: Options = Options(),
    timeFrom: Option[String] = None, //ToDo time model
    interval: Option[String] = None,
    fieldConfig: Option[FieldConfig] = None
)

object Panel {

  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Panel] = deriveConfiguredCodec[Panel]
}
