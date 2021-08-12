package scalograf
package model.transformations

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import io.circe.{Codec, JsonObject}

case class Organize(
    excludeByName: Map[String, Boolean] = Map.empty,
    indexByName: Map[String, Int] = Map.empty,
    renameByName: Map[String, String] = Map.empty
) extends Transformation {
  override def id: String = "organize"

  override def toJson: JsonObject = Organize.codec.encodeObject(this)
}

object Organize {
  implicit val codecConfig: Configuration = Configuration.default
  implicit val codec: Codec.AsObject[Organize] = deriveConfiguredCodec[Organize]
}
