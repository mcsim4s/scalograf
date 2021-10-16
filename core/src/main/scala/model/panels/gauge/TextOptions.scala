package scalograf
package model.panels.gauge

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class TextOptions(
    titleSize: Option[Int] = None,
    valueSize: Option[Int] = None
)

object TextOptions {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[TextOptions]
}
