package scalograf
package model.panels.logs

import model.panels.{GridPosition, Panel}
import model.{Target, Time}

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Logs(
    options: Options = Options(),
    targets: List[Target] = List.empty,
    timeFrom: Option[Time] = None,
    timeShift: Option[Time] = None
) extends Panel.Type {
  override def `type`: String = "logs"

  override def asJson: JsonObject = Logs.codec.encodeObject(this)
}

object Logs {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Logs] = deriveConfiguredCodec[Logs]
}
