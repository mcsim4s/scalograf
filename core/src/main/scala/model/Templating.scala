package scalograf
package model

import io.circe.generic.extras.semiauto._

case class Templating(list: List[String])

object Templating {
  implicit val templatingCodec = deriveConfiguredCodec[Templating]
}
