package scalograf
package model.panels.graph

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
  implicit val encoder = deriveConfiguredEncoder[Tooltip].mapJson(
    _.mapObject(obj =>
      obj("valueType") match {
        case Some(value) => obj.add("value_type", value)
        case None        => obj
      }
    )
  )
  implicit val decoder = deriveConfiguredDecoder[Tooltip].prepare(
    _.withFocus(
      _.mapObject(obj =>
        obj("value_type") match {
          case Some(value) => obj.add("valueType", value)
          case None        => obj
        }
      )
    )
  )
}
