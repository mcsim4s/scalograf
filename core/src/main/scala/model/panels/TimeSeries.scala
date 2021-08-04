package scalograf
package model.panels

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class TimeSeries(
    id: Int,
    description: Option[String] = None,
    gridPos: GridPosition,
    title: Option[String] = None,
    datasource: Option[String] = None
) extends Panel {
  override def `type`: String = "timeseries"

  override def asJson: JsonObject = TimeSeries.codec.encodeObject(this)
}

object TimeSeries {
  implicit val codecConfig = Configuration.default.withDefaults
  implicit val codec: Codec.AsObject[TimeSeries] = deriveConfiguredCodec[TimeSeries]
}
