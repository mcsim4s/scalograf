package scalograf
package model.panels

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class Logs(
    id: Int,
    gridPos: GridPosition,
    description: Option[String] = None,
    title: Option[String] = None,
    datasource: Option[String] = None
) extends Panel {
  override def `type`: String = "logs"

  override def asJson: JsonObject = Logs.codec.encodeObject(this)
}

object Logs {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Logs] = deriveConfiguredCodec[Logs]
}
