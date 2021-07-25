package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Transformation()

object Transformation {
  implicit val config = Configuration.default
  implicit val codec = deriveConfiguredCodec[Transformation]
}
