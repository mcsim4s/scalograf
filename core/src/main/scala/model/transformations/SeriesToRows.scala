package scalograf
package model.transformations

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import io.circe.{Codec, JsonObject}

case class SeriesToRows(
) extends Transformation {
  override def id: String = "seriesToRows"

  override def toJson: JsonObject = SeriesToRows.codec.encodeObject(this)
}

object SeriesToRows {

  implicit val codecConfig: Configuration = Configuration.default
  implicit val codec: Codec.AsObject[SeriesToRows] = deriveConfiguredCodec[SeriesToRows]
}
