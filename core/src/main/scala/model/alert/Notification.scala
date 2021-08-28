package scalograf
package model.alert

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

case class Notification()

object Notification {

  implicit val codecConfig = Configuration.default
  implicit val codec = deriveConfiguredCodec[Notification]
}
