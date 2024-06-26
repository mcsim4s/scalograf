package scalograf
package model.panels.timeseries

import model.panels.config.FieldConfig.CustomFieldConfig
import model.panels.timeseries.TimeSeriesConfig._
import model.panels.{ScaleDistribution, ThresholdStyle}

import io.circe.Decoder.Result
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec
import io.circe.syntax._
import io.circe.{Codec, HCursor, Json}

import scala.concurrent.duration.{FiniteDuration, MILLISECONDS}

case class TimeSeriesConfig(
    axisLabel: Option[String] = None,
    axisPlacement: Option[AxisPlacement] = None,
    barAlignment: Option[Int] = None,
    drawStyle: Option[DrawStyle] = None,
    fillOpacity: Option[Int] = None,
    gradientMode: Option[GradientMode] = None,
    lineInterpolation: Option[LineInterpolation] = None,
    lineWidth: Option[Int] = None,
    pointSize: Option[Int] = None,
    scaleDistribution: Option[ScaleDistribution] = None,
    showPoints: Option[ShowPoints] = None,
    spanNulls: Option[SpanNulls] = None,
    stacking: Option[Stacking] = None,
    thresholdsStyle: Option[ThresholdStyle] = None
) extends CustomFieldConfig

object TimeSeriesConfig {
  case class HideFrom(
      legend: Boolean = false,
      tooltip: Boolean = false,
      viz: Boolean = false
  )
  case class Stacking(group: String, mode: StackingMode = StackingMode.None)

  sealed trait SpanNulls
  case class ConnectNulls(value: Boolean) extends SpanNulls
  case class ConnectNullsWithThreshold(threshold: FiniteDuration) extends SpanNulls

  implicit val config = Configuration.default.withDefaults
  implicit val codecSpanNulls = new Codec[SpanNulls] {
    override def apply(c: HCursor): Result[SpanNulls] =
      c.as[Boolean]
        .map(ConnectNulls.apply)
        .orElse(c.as[Long].map(m => FiniteDuration(m, MILLISECONDS)).map(ConnectNullsWithThreshold.apply))

    override def apply(a: SpanNulls): Json =
      a match {
        case ConnectNulls(value)                  => value.asJson
        case ConnectNullsWithThreshold(threshold) => threshold.toMillis.asJson
      }
  }
  implicit val codecHideFrom = deriveConfiguredCodec[HideFrom]
  implicit val codecStacking = deriveConfiguredCodec[Stacking]
  implicit val codec = deriveConfiguredCodec[TimeSeriesConfig]
}
