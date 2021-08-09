package scalograf
package client

import model.Dashboard

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class DashboardUploadRequest(
    dashboard: Dashboard,
    folderId: Option[Int] = None,
    folderUid: Option[String] = None,
    message: Option[String] = None,
    overwrite: Boolean = false
)

object DashboardUploadRequest {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[DashboardUploadRequest]
}
