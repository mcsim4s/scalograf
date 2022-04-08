package scalograf
package model.folder

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}

case class FolderInfo(id: Long, uid: String, title: String)

object FolderInfo {
  implicit val config = Configuration.default.withDefaults
  implicit val encoder = deriveConfiguredEncoder[FolderInfo].mapJson(_.deepDropNullValues)
  implicit val decoder = deriveConfiguredDecoder[FolderInfo]
}
