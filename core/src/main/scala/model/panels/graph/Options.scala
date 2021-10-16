package scalograf
package model.panels.graph

import model.link.Link

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Options(
    alertThreshold: Boolean = false,
    dataLinks: List[Link] = List.empty
)

object Options {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Options] = deriveConfiguredCodec[Options]
}
