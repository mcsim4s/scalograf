package scalograf
package client

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}
import io.circe.{Decoder, Encoder}

case class ErrorResponse(
    message: String
)

object ErrorResponse {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val encoder: Encoder[ErrorResponse] =
    deriveConfiguredEncoder[ErrorResponse].mapJson(_.deepDropNullValues)
  implicit val decoder: Decoder[ErrorResponse] = deriveConfiguredDecoder[ErrorResponse]
}
