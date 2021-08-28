package scalograf
package model.alert.condition.query

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Reducer(
    params: List[Double],
    `type`: ReducerType
)

object Reducer {

  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Reducer] = deriveConfiguredCodec[Reducer]
}
