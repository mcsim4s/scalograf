package scalograf
package model.alert

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class NoDataBehaviour(val value: String) extends StringEnumEntry

object NoDataBehaviour extends StringEnum[NoDataBehaviour] with StringCirceEnum[NoDataBehaviour] {
  val values = findValues

  case object Alerting extends NoDataBehaviour("alerting")
  case object NoData extends NoDataBehaviour("no_data")
  case object KeepLastState extends NoDataBehaviour("keep_state")
  case object Ok extends NoDataBehaviour("ok")
}
