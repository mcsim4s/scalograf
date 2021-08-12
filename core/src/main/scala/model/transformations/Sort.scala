package scalograf
package model.transformations

import model.transformations.Sort._

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import io.circe.{Codec, JsonObject}

case class Sort(
    sort: List[By] = List.empty
) extends Transformation {
  override def id: String = "sortBy"

  override def toJson: JsonObject = Sort.codec.encodeObject(this)
}

object Sort {
  case class By(field: String, desc: Boolean = false)

  implicit val codecConfig: Configuration = Configuration.default
  implicit val codecBy: Codec.AsObject[By] = deriveConfiguredCodec[By]
  implicit val codec: Codec.AsObject[Sort] = deriveConfiguredCodec[Sort]
}
