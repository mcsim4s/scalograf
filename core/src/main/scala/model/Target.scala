package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Target(
    expr: String,
    refId: String,
    legendFormat: String = "",
    range: Boolean = false,
    instant: Boolean = false,
    maxLines: Option[Int] = None
)

object Target {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Target]
}
