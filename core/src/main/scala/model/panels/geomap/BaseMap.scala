package scalograf
package model.panels.geomap

import model.panels.geomap.BaseMap._

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class BaseMap(
    config: BaseMapConfig = BaseMapConfig(),
    `type`: String = "esri-xyz" //ToDo enum
)

object BaseMap {
  case class BaseMapConfig(
      server: String = "streets" //ToDo enum
  )

  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val configCodec: Codec.AsObject[BaseMapConfig] = deriveConfiguredCodec[BaseMapConfig]
  implicit val codec: Codec.AsObject[BaseMap] = deriveConfiguredCodec[BaseMap]
}
