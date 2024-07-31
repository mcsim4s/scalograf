package scalograf
package model.panels

import model.panels.Legend._

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Legend(
    calcs: List[String] = List.empty,
    displayMode: DisplayMode = DisplayMode.List,
    placement: Placement = Placement.Bottom,
    width: Option[Int] = None,
    sortBy: Option[String] = None,
    sortDesc: Option[Boolean] = None
)

object Legend {
  sealed abstract class DisplayMode(val value: String) extends StringEnumEntry

  object DisplayMode extends StringEnum[DisplayMode] with StringCirceEnum[DisplayMode] {
    val values = findValues

    case object List extends DisplayMode("list")
    case object Table extends DisplayMode("table")
    case object Hidden extends DisplayMode("hidden")
  }
  sealed abstract class Placement(val value: String) extends StringEnumEntry

  object Placement extends StringEnum[Placement] with StringCirceEnum[Placement] {
    val values = findValues

    case object Bottom extends Placement("bottom")
    case object Right extends Placement("right")
  }

  implicit val config = Configuration.default.withDefaults
  implicit val codec = deriveConfiguredCodec[Legend]
}
