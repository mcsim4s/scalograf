package scalograf
package model.template

import io.circe.JsonObject
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class ConstantTemplate(
    query: String = ""
) extends Template.Type {
  override def `type`: String = "constant"

  override def asJson: JsonObject = ConstantTemplate.codec.encodeObject(this)
}

object ConstantTemplate {

  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[ConstantTemplate]
}
