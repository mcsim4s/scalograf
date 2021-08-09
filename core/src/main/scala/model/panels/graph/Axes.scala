package scalograf
package model.panels.graph

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Axes(
    format: String, //ToDe enum
    label: Option[String] = None,
    logBase: Double,
    max: Option[Double] = None,
    min: Option[Double] = None,
    show: Boolean = false
)

object Axes {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Axes]
}
