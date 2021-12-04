package scalograf
package client

import model.datasource.Datasource

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}
import io.circe.{Decoder, Encoder}

case class DatasourceCreateResponse(
    datasource: Datasource,
    id: Long,
    message: String,
    name: String
)

object DatasourceCreateResponse {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val encoder: Encoder[DatasourceCreateResponse] =
    deriveConfiguredEncoder[DatasourceCreateResponse].mapJson(_.deepDropNullValues)
  implicit val decoder: Decoder[DatasourceCreateResponse] = deriveConfiguredDecoder[DatasourceCreateResponse]
}
