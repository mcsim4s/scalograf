package scalograf
package model

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

case class Panel( //collapsed: Boolean = false,
    id: Long,
    gridPos: GridPosition,
    panels: List[Panel] = List.empty,
    datasource: Option[String] = None, //ToDo ???
    title: Option[String] = None,
    `type`: String, //ToDo enum
    description: Option[String] = None
)

object Panel {

  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Panel] = deriveConfiguredCodec[Panel]
}
