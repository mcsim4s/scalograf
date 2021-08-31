package scalograf
package model.template.adhoc

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class AdHocFilter(
    key: Option[String],
    operator: AdHocOperator = AdHocOperator.Equals,
    value: Option[String]
)

object AdHocFilter {
  implicit val codecConfig = Configuration.default
  implicit val codec = deriveConfiguredCodec[AdHocFilter]
}
