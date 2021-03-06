package scalograf
package model.panels.table

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class SortBy(
    desc: Boolean = false,
    displayName: Option[String] = None,
    col: Option[Int] = None
)

object SortBy {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[SortBy]
}
