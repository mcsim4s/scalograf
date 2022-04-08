package scalograf
package client.api.folders

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}

case class UpdateFolderRequest(uid: String, title: String, version: Option[Long] = None, `overwrite`: Boolean = true)

object UpdateFolderRequest {
  implicit val config = Configuration.default.withDefaults
  implicit val encoder = deriveConfiguredEncoder[UpdateFolderRequest].mapJson(_.deepDropNullValues)
  implicit val decoder = deriveConfiguredDecoder[UpdateFolderRequest]
}
