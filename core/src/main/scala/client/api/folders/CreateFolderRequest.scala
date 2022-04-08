package scalograf
package client.api.folders

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}

case class CreateFolderRequest(uid: String, title: String)

object CreateFolderRequest {
  implicit val config = Configuration.default.withDefaults
  implicit val encoder = deriveConfiguredEncoder[CreateFolderRequest].mapJson(_.deepDropNullValues)
  implicit val decoder = deriveConfiguredDecoder[CreateFolderRequest]
}
