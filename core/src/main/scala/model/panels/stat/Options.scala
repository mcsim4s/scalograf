package scalograf
package model.panels.stat

import model.Text

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Options(
    colorMode: String = "background", // ToDo enum
    graphMode: Option[String] = None, //ToDo enum
    justifyMode: String = "center", // ToDo enum
    orientation: String = "auto", //ToDo enum,
    reduceOptions: Option[ReduceOptions] = None,
    text: Option[Text] = None,
    textMode: String = "value" // ToDo enum
)

object Options {
  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Options]
}
