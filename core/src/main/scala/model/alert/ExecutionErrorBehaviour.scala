package scalograf
package model.alert

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class ExecutionErrorBehaviour(val value: String) extends StringEnumEntry

object ExecutionErrorBehaviour
    extends StringEnum[ExecutionErrorBehaviour]
    with StringCirceEnum[ExecutionErrorBehaviour] {
  val values = findValues

  case object Alerting extends ExecutionErrorBehaviour("alerting")
  case object KeepLastState extends ExecutionErrorBehaviour("keep_state")
}
