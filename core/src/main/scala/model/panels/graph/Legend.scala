package scalograf
package model.panels.graph

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Legend(
    alignAsTable: Boolean = false,
    avg: Boolean = false,
    current: Boolean = false,
    hideEmpty: Boolean = false,
    hideZero: Boolean = false,
    max: Boolean = false,
    min: Boolean = false,
    rightSide: Boolean = false,
    show: Boolean = false,
    sideWidth: Option[Int] = None,
    sort: Option[String] = None, //ToDo enum
    sortDesc: Option[Boolean] = None,
    total: Boolean = false,
    values: Boolean = false
)

object Legend {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Legend]
}
