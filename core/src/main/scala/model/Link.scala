package scalograf
package model

import io.circe.generic.extras.semiauto._

case class Link()

object Link {
  implicit val linkCodec = deriveConfiguredCodec[Link]
}
