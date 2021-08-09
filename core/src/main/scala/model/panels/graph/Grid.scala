package scalograf
package model.panels.graph

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Grid(
)

object Grid {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Grid]
}
