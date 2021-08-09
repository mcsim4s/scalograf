package scalograf
package model.panels.singlestat

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class ValueMap(
    op: String,
    text: String,
    value: String
)

object ValueMap {
  implicit val codecConfig = Configuration.default
  implicit val codec = deriveConfiguredCodec[ValueMap]
}
