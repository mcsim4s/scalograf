package scalograf
package model.alert

import model.alert.condition.Condition
import model.time._

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

import scala.concurrent.duration.FiniteDuration

case class Alert(
    alertRuleTags: Map[String, String] = Map.empty,
    conditions: List[Condition],
    name: Option[String] = None,
    executionErrorState: ExecutionErrorBehaviour = ExecutionErrorBehaviour.Alerting,
    noDataState: NoDataBehaviour = NoDataBehaviour.Alerting,
    `for`: Option[FiniteDuration] = None,
    frequency: FiniteDuration,
    notifications: List[Notification] = List.empty,
    handler: Int = 1 // I have no idea what this is :(
)

object Alert {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Alert]
}
