package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._
import Mappings._

case class Mappings(options: Map[String, Mapping], `type`: Option[String] = None)

object Mappings {
  case class Mapping(color: Option[Color] = None, index: Int, text: String)

  implicit val config = Configuration.default.withDefaults

  implicit val mappingCodec = deriveConfiguredCodec[Mapping]
  implicit val codec = deriveConfiguredCodec[Mappings]
}
