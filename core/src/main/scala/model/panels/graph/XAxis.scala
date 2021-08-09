package scalograf
package model.panels.graph

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class XAxis(
    align: Boolean = false,
    alignLevel: Option[String] = None,
    buckets: Option[String] = None, //ToDo buckets model
    mode: Option[String] = None, //ToDo enum
    name: Option[String] = None,
    show: Boolean = false,
    values: List[String] = List.empty
)

object XAxis {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[XAxis]
}
