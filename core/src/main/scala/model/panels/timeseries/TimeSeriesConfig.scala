package scalograf
package model.panels.timeseries

import model.panels.FieldConfig.CustomFieldConfig

import TimeSeriesConfig._
import io.circe.Decoder.Result
import io.circe.{Codec, DecodingFailure, HCursor, JsonObject}
import io.circe.syntax._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

case class TimeSeriesConfig(
    axisLabel: String = "",
    axisPlacement: String = "auto", //ToDo enum
    barAlignment: Int = 0,
    drawStyle: String = "line", //ToDo enum
    fillOpacity: Int = 0,
    gradientMode: String = "none", //ToDo enum
    hideFrom: HideFrom = HideFrom(),
    lineInterpolation: String = "smooth", //ToDo enum
    lineWidth: Int = 1,
    pointSize: Int = 3,
    scaleDistribution: ScaleDistribution = Linear,
    showPoints: String = "auto", //ToDo enum
    spanNulls: Boolean = false,
    stacking: Option[Stacking] = None,
    thresholdsStyle: ThresholdStyle = ThresholdStyle()
) extends CustomFieldConfig

object TimeSeriesConfig {
  case class ThresholdStyle(mode: String = "off") //ToDo enum
  case class HideFrom(
      legend: Boolean = false,
      tooltip: Boolean = false,
      viz: Boolean = false
  )
  case class Stacking(group: String, mode: String = "none") //ToDo enum

  sealed trait ScaleDistribution {
    def `type`: String
  }

  object Linear extends ScaleDistribution {
    override def `type`: String = "linear"
  }

  case class LogScale(log: Int) extends ScaleDistribution {
    override def `type`: String = "log"
  }

  implicit val config = Configuration.default.withDefaults
  implicit val codecScaleDistribution = new Codec.AsObject[ScaleDistribution] {
    override def apply(c: HCursor): Result[ScaleDistribution] =
      c.downField("type").as[String].flatMap {
        case "linear" => Right(Linear)
        case "log"    => c.downField("log").as[Int].map(LogScale.apply)
        case other    => Left(DecodingFailure(s"Unknown scale distribution type: $other", c.history))
      }

    override def encodeObject(d: ScaleDistribution): JsonObject =
      d match {
        case Linear        => JsonObject("type" -> "linear".asJson)
        case LogScale(log) => JsonObject("type" -> "log".asJson, "log" -> log.asJson)
      }
  }
  implicit val codecThresholdStyle = deriveConfiguredCodec[ThresholdStyle]
  implicit val codecHideFrom = deriveConfiguredCodec[HideFrom]
  implicit val codecStacking = deriveConfiguredCodec[Stacking]
  implicit val codec = deriveConfiguredCodec[TimeSeriesConfig]
}
