package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

case class Link()

object Link {

  implicit val codecConfig = Configuration.default
  implicit val codec = deriveConfiguredCodec[Link]
}
