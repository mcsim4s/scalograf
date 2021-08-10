package scalograf
package model.panels.graph

import model._

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}

case class Tooltip(
    msResolution: Boolean = false,
    shared: Boolean = false,
    sort: Int,
    valueType: String //ToDo enum
)

object Tooltip {

  implicit val config = Configuration.default.withDefaults
  implicit val encoder = deriveConfiguredEncoder[Tooltip]
    .mapJson(changeEncodeName("valueType", "value_type"))

  implicit val decoder = deriveConfiguredDecoder[Tooltip]
    .prepare(changeDecodeName("value_type", "valueType"))
}
