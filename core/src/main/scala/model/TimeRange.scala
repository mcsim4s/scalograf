package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class TimeRange(from: String, to: String)

object TimeRange {
  implicit val codecConfig = Configuration.default
  implicit val codec = deriveConfiguredCodec[TimeRange]
}
