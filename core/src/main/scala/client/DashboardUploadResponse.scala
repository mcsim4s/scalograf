package scalograf
package client

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}
import io.circe.{Decoder, Encoder}

case class DashboardUploadResponse(
    id: Long,
    slug: String,
    status: String,
    uid: String,
    url: String,
    version: Long
)

object DashboardUploadResponse {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val encoder: Encoder[DashboardUploadResponse] =
    deriveConfiguredEncoder[DashboardUploadResponse].mapJson(_.deepDropNullValues)
  implicit val decoder: Decoder[DashboardUploadResponse] = deriveConfiguredDecoder[DashboardUploadResponse]
}
