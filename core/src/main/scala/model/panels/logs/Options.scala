package scalograf
package model.panels.logs

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Options(
    dedupStrategy: Option[String] = None,
    enableLogDetails: Boolean = false,
    prettifyLogMessage: Boolean = false,
    showCommonLabels: Boolean = false,
    showLabels: Boolean = false,
    showTime: Boolean = false,
    sortOrder: String = "Descending", //toDo enum
    wrapLogMessage: Boolean = false
)

object Options {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Options]
}
