package scalograf
package client

import model.Dashboard

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}

case class DashboardUploadRequest(
    dashboard: Dashboard,
    folderId: Option[Int] = None,
    folderUid: Option[String] = None,
    message: Option[String] = None,
    overwrite: Boolean = false
)

object DashboardUploadRequest {
  implicit val config = Configuration.default.withDefaults
  implicit val encoder = deriveConfiguredEncoder[DashboardUploadRequest].mapJson(_.deepDropNullValues)
  implicit val decoder = deriveConfiguredDecoder[DashboardUploadRequest]
}
