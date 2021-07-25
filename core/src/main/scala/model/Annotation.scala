package scalograf
package model

import io.circe.generic.extras.semiauto._

case class Annotation(name: String)

object Annotation {
  implicit val annotationCodec = deriveConfiguredCodec[Annotation]
}
