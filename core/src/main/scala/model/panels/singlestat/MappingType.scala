package scalograf
package model.panels.singlestat

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class MappingType(name: String, value: Int)

object MappingType {
  implicit val codecConfig = Configuration.default
  implicit val codec = deriveConfiguredCodec[MappingType]
}
