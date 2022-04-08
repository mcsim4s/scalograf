package scalograf
package client

import model.Dashboard

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}
import io.circe.{Decoder, Encoder}

case class GetDashboardResponse(dashboard: Dashboard)

object GetDashboardResponse {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val encoder: Encoder[GetDashboardResponse] =
    deriveConfiguredEncoder[GetDashboardResponse].mapJson(_.deepDropNullValues)
  implicit val decoder: Decoder[GetDashboardResponse] = deriveConfiguredDecoder[GetDashboardResponse]
}
