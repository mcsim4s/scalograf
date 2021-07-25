package scalograf
package model

import io.circe.generic.extras.semiauto._

case class DashboardSnippet(id: Long, uid: String, uri: String)

object DashboardSnippet {
  implicit val dashboardSnippetCodec = deriveConfiguredCodec[DashboardSnippet]
}
