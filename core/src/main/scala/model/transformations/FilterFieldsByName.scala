package scalograf
package model.transformations

import model.transformations.FilterFieldsByName._

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import io.circe.{Codec, JsonObject}

case class FilterFieldsByName(
    include: Filter = Filter()
) extends Transformation {
  override def id: String = "filterFieldsByName"

  override def toJson: JsonObject = FilterFieldsByName.codec.encodeObject(this)
}

object FilterFieldsByName {
  case class Filter(names: List[String] = List.empty)

  implicit val codecConfig: Configuration = Configuration.default
  implicit val codecFilter: Codec.AsObject[Filter] = deriveConfiguredCodec[Filter]
  implicit val codec: Codec.AsObject[FilterFieldsByName] = deriveConfiguredCodec[FilterFieldsByName]
}
