package scalograf
package model.panels.graph

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class YAxis(
    align: Boolean = false,
    alignLevel: Option[String] = None
)

object YAxis {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[YAxis]
}
