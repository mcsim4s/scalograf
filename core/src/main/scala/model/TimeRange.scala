package scalograf
package model

import model.time.Time

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class TimeRange(from: Time, to: Time)

object TimeRange {
  implicit val codecConfig = Configuration.default
  implicit val codec = deriveConfiguredCodec[TimeRange]
}
