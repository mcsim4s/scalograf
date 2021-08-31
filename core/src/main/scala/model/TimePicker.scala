package scalograf
package model

import time._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

import scala.concurrent.duration.FiniteDuration

case class TimePicker(
    nowDelay: Option[FiniteDuration] = None,
    refreshIntervals: List[String] = List.empty,
    timeOptions: List[String] = List.empty
)

object TimePicker {
  implicit val conf = Configuration.default.withSnakeCaseMemberNames.withDefaults
  implicit val codec = deriveConfiguredCodec[TimePicker]
}
