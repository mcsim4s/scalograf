package scalograf
package model

import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class TimePicker()

object TimePicker {
  implicit val timePickerCodec = deriveConfiguredCodec[TimePicker]
}
