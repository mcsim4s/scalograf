package scalograf
package model.alert.condition.query

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Evaluator(
    `type`: EvaluationType,
    params: List[Double]
)

object Evaluator {
  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Evaluator] = deriveConfiguredCodec[Evaluator]
}
