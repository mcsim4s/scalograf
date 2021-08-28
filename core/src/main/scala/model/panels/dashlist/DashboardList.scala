package scalograf
package model.panels.dashlist

import model.panels.Panel

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import scalograf.model.Link

case class DashboardList(
    folderId: Option[Int] = None,
    headings: Boolean = false,
    limit: Option[Int] = None,
    links: List[Link] = List.empty,
    query: Option[String] = None,
    recent: Boolean = false,
    search: Boolean = false,
    starred: Boolean = false,
    tags: List[String] = List.empty,
    transparent: Boolean = false
) extends Panel.Type {
  override def `type`: String = "dashlist"

  override def asJson: JsonObject = DashboardList.codec.encodeObject(this)
}

object DashboardList {

  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[DashboardList] = deriveConfiguredCodec[DashboardList]
}
