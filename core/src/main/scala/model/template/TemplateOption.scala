package scalograf
package model.template

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class TemplateOption(
    selected: Boolean = false,
    text: String = "",
    value: String = "",
    tags: List[String] = List.empty
)

object TemplateOption {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[TemplateOption]
}
