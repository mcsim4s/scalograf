package scalograf
package model.panels.singlestat

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class RangeMap(
    from: String,
    text: String,
    to: String
)

object RangeMap {
  implicit val codecConfig = Configuration.default
  implicit val codec = deriveConfiguredCodec[RangeMap]
}
