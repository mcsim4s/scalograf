package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Requirement(name: String, `type`: String, id: String, version: String)

object Requirement {
  implicit val codecConfig = Configuration.default
  implicit val codec = deriveConfiguredCodec[Requirement]
}
