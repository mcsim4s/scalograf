package scalograf
package model.annotations

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class AnnotationTarget(
    limit: Option[Int] = None,
    matchAny: Boolean = false,
    tags: List[String] = List.empty,
    `type`: String //Todo enum???
)

object AnnotationTarget {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[AnnotationTarget]
}
