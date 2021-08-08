package scalograf
package model.panels

import io.circe.Json
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

import Override._

case class Override(
    matcher: Matcher,
    properties: List[Property] = List.empty //ToDo properties ADT
)

object Override {
  case class Matcher(id: String, options: String)
  case class Property(
      id: String,
      value: Json
  )

  implicit val config = Configuration.default.withDefaults
  implicit val codecMatcher = deriveConfiguredCodec[Matcher]
  implicit val codecProperty = deriveConfiguredCodec[Property]
  implicit val codec = deriveConfiguredCodec[Override]
}
