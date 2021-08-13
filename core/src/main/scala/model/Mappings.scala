package scalograf
package model

import model.Mappings._

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

case class Mappings(options: Map[String, Mapping], `type`: String)

object Mappings {
  case class Mapping(index: Int, text: String, color: Option[Color] = None)

  implicit val config = Configuration.default.withDefaults

  implicit val mappingCodec = deriveConfiguredCodec[Mapping]
  implicit val codec = deriveConfiguredCodec[Mappings]
}
