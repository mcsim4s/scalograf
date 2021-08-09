package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Target(
    expr: String,
    format: Option[String] = None,
    instant: Boolean = false,
    intervalFactor: Option[Double] = None,
    legendFormat: String = "",
    maxLines: Option[Int] = None,
    range: Boolean = false,
    refId: String,
    step: Option[Double] = None
)

object Target {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Target]
}
