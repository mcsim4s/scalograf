package scalograf
package model.alert

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

case class Notification(
    uid: Option[String]
)

object Notification {

  implicit val codecConfig: Configuration = Configuration.default
  implicit val codec: Codec.AsObject[Notification] = deriveConfiguredCodec[Notification]
}
