package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class SortBy(
    desc: Boolean = false,
    displayName: Option[String] = None
)

object SortBy {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[SortBy]
}
