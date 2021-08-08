package scalograf
package model

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Input(name: String, label: String, description: String, `type`: String, pluginId: String, pluginName: String)

object Input {
  implicit val codecConfig = Configuration.default
  implicit val codec = deriveConfiguredCodec[Input]
}
