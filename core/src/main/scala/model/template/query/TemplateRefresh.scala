package scalograf
package model.template.query

import enumeratum.values.{IntCirceEnum, IntEnum, IntEnumEntry}

sealed abstract class TemplateRefresh(val value: Int) extends IntEnumEntry

object TemplateRefresh extends IntEnum[TemplateRefresh] with IntCirceEnum[TemplateRefresh] {
  val values = findValues

  case object OnLoad extends TemplateRefresh(0)
  case object OnTimeRangeChange extends TemplateRefresh(1)
}
