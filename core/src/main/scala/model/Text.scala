package scalograf
package model

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

case class Text(
    titleSize: Option[Int] = None,
    valueSize: Option[Int] = None
)

object Text {

  implicit val codecConfig: Configuration = Configuration.default
  implicit val codec: Codec.AsObject[Text] = deriveConfiguredCodec[Text]
}
