package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

case class Annotation(
    `type`: String, // ToDo enum
    builtIn: Int = 0, // ToDo what is it ???
    datasource: String,
    enable: Boolean = true,
    hide: Boolean = false,
    iconColor: Color,
    name: String
)

object Annotation {
  implicit val codecConfig = Configuration.default
  implicit val codec = deriveConfiguredCodec[Annotation]
}
