package scalograf
package model.template.query

import enumeratum.values.{IntCirceEnum, IntEnum, IntEnumEntry}

sealed abstract class ValuesSort(val value: Int) extends IntEnumEntry

object ValuesSort extends IntEnum[ValuesSort] with IntCirceEnum[ValuesSort] {
  val values = findValues

  case object Disabled extends ValuesSort(0)
  case object AlphabeticalAsc extends ValuesSort(1)
  case object AlphabeticalDesc extends ValuesSort(2)
  case object NumericalAsc extends ValuesSort(3)
  case object NumericalDesc extends ValuesSort(4)
  case object AlphabeticalAscCaseInsensitive extends ValuesSort(5)
  case object AlphabeticalDescCaseInsensitive extends ValuesSort(6)
}
