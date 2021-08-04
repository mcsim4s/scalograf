package scalograf
package model.panels.table

import model.SortBy

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Options(
    showHeader: Boolean = true,
    sortBy: Option[SortBy] = None
)

object Options {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Options]
}
