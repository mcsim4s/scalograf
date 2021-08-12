package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class TimePicker(
    nowDelay: Option[String] = None, //ToDo time model
    refreshIntervals: List[String] = List.empty,
    timeOptions: List[String] = List.empty
)

object TimePicker {
  implicit val conf = Configuration.default.withSnakeCaseMemberNames.withDefaults
  implicit val codec = deriveConfiguredCodec[TimePicker]
}
