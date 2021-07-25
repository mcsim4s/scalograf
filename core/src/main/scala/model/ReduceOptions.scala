package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class ReduceOptions(
    calcs: List[String], //ToDo enums ???
    fields: String,
    values: Boolean
)

object ReduceOptions {
  implicit val config = Configuration.default
  implicit val codec = deriveConfiguredCodec[ReduceOptions]
}
