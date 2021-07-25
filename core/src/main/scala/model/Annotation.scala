package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

case class Annotation(name: String)

object Annotation {
  implicit val codecConfig = Configuration.default
  implicit val annotationCodec = deriveConfiguredCodec[Annotation]
}
