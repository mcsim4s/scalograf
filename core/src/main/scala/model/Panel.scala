package scalograf
package model

import io.circe.generic.extras.semiauto._

case class Panel()

object Panel {
  implicit val panelCodec = deriveConfiguredCodec[Panel]
}
