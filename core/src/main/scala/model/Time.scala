package scalograf
package model

import io.circe.generic.extras.semiauto._

case class Time()

object Time {
  val default = Time()

  implicit val timeCodec = deriveConfiguredCodec[Time]
}
