package scalograf
package model.folder

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}

import java.time.Instant

case class Folder(
    id: Long,
    uid: String,
    title: String,
    url: String,
    hasAcl: Boolean,
    canSave: Boolean,
    canEdit: Boolean,
    canAdmin: Boolean,
    createdBy: String,
    created: Instant,
    updatedBy: String,
    updated: Instant,
    version: Long
)

object Folder {
  implicit val config = Configuration.default.withDefaults
  implicit val encoder = deriveConfiguredEncoder[Folder].mapJson(_.deepDropNullValues)
  implicit val decoder = deriveConfiguredDecoder[Folder]
}
