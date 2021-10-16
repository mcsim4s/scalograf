package scalograf
package model.panels.graph

import model.Color

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class SeriesOverrides(
    alias: String,
    color: Option[Color] = None,
    stack: Option[Boolean] = None,
    fill: Option[Int] = None,
    transform: Option[String] = None //ToDo enum ???
)

object SeriesOverrides {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[SeriesOverrides] = deriveConfiguredCodec[SeriesOverrides]
}
