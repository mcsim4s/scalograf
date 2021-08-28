package scalograf
package model.panels.logs

import model.Target
import model.panels.Panel
import model.time._

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

import scala.concurrent.duration.FiniteDuration

case class Logs(
    options: Options = Options(),
    targets: List[Target] = List.empty,
    timeFrom: Option[FiniteDuration] = None,
    timeShift: Option[FiniteDuration] = None
) extends Panel.Type {
  override def `type`: String = "logs"

  override def asJson: JsonObject = Logs.codec.encodeObject(this)
}

object Logs {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[Logs] = deriveConfiguredCodec[Logs]
}
