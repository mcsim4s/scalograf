package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

case class Template()

object Template {
  implicit val codecConfig = Configuration.default
  implicit val templateCodec = deriveConfiguredCodec[Template]
}
