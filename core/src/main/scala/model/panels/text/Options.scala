package scalograf
package model.panels.text

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Options(
    content: Option[String] = None,
    mode: Option[String] = None // ToDo enum
)

object Options {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Options]
}
