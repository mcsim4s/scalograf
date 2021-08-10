package scalograf
package model.panels.table

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Column(
    text: String,
    value: String
)

object Column {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Column]
}
