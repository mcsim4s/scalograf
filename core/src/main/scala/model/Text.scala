package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

case class Text()

object Text {

  implicit val codecConfig = Configuration.default
  implicit val codec = deriveConfiguredCodec[Text]
}
