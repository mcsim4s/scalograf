package scalograf
package model

import io.circe.generic.extras.semiauto._

case class Annotations(list: List[Annotation])

object Annotations {
  val empty = Annotations(List.empty)

  implicit val annotationsCodec = deriveConfiguredCodec[Annotations]
}
