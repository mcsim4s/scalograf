package scalograf
package model.panels.singlestat

import model.Color

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class SparkLine(
    fillColor: Color,
    full: Boolean,
    lineColor: Color,
    show: Boolean
)

object SparkLine {
  implicit val codecConfig: Configuration = Configuration.default
  implicit val codec: Codec.AsObject[SparkLine] = deriveConfiguredCodec[SparkLine]
}
