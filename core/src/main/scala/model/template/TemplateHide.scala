package scalograf
package model.template

import enumeratum.values.{IntCirceEnum, IntEnum, IntEnumEntry}

sealed abstract class TemplateHide(val value: Int) extends IntEnumEntry

object TemplateHide extends IntEnum[TemplateHide] with IntCirceEnum[TemplateHide] {
  val values = findValues

  case object None extends TemplateHide(0)
  case object Label extends TemplateHide(1)
  case object Variable extends TemplateHide(2)
}
