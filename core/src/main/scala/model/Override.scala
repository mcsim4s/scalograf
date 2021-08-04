package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Override()

object Override {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Override]
}
