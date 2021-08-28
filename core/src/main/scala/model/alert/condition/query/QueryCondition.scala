package scalograf
package model.alert.condition.query

import model.alert.condition.Condition
import model.alert.condition.query.QueryCondition._

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

case class QueryCondition(
    evaluator: Evaluator,
    query: Query,
    operator: Operator,
    reducer: Reducer
) extends Condition {
  override def `type`: String = "query"
}

object QueryCondition {

  implicit val config: Configuration = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[QueryCondition] = deriveConfiguredCodec[QueryCondition]
}
