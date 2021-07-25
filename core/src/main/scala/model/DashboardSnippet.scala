package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

case class DashboardSnippet(id: Long, uid: String, uri: String)

object DashboardSnippet {
  implicit val codecConfig = Configuration.default
  implicit val dashboardSnippetCodec = deriveConfiguredCodec[DashboardSnippet]
}
