package scalograf
package model.panels.row

import model.panels.{GridPosition, Panel}

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Row(
    collapsed: Boolean = false,
    panels: List[Panel] = List.empty,
    repeat: Option[Json] = None
) extends Panel.Type {
  override def `type`: String = "row"

  override def asJson: JsonObject = Row.codec.encodeObject(this)
}

object Row {

  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Row] = deriveConfiguredCodec[Row]
}
