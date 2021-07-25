package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class TimePicker(refreshIntervals: List[String])

object TimePicker {
  val empty: TimePicker = TimePicker(List.empty)

  implicit val conf = Configuration.default.withSnakeCaseMemberNames
  implicit val codec = deriveConfiguredCodec[TimePicker]
}
