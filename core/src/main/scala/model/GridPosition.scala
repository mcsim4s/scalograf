package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

case class GridPosition(h: Int, w: Int, x: Int, y: Int)

object GridPosition {
  implicit val codecConfig = Configuration.default
  implicit val codec = deriveConfiguredCodec[GridPosition]
}
