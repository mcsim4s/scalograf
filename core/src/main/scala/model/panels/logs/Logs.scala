package scalograf
package model.panels.logs

import model.{Target, Time}
import model.panels.{GridPosition, Panel}

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Logs(
    datasource: Option[String] = None,
    description: Option[String] = None,
    gridPos: GridPosition,
    id: Option[Int] = None,
    options: Options = Options(),
    targets: List[Target] = List.empty,
    timeFrom: Option[Time] = None,
    timeShift: Option[Time] = None,
    title: Option[String] = None
) extends Panel {
  override def `type`: String = "logs"

  override def asJson: JsonObject = Logs.codec.encodeObject(this)
}

object Logs {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Logs] = deriveConfiguredCodec[Logs]
}
