package scalograf
package model.enums

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}

sealed abstract class DashboardStyle(val value: String) extends StringEnumEntry

object DashboardStyle extends StringEnum[DashboardStyle] with StringCirceEnum[DashboardStyle] {
  val values = findValues

  case object Default extends DashboardStyle("default")
  case object Dark extends DashboardStyle("dark")
}
