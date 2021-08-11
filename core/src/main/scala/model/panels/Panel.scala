package scalograf
package model.panels

import model.panels.graph.Graph
import model.panels.logs.Logs
import model.panels.map.WorldMapPanel
import model.panels.row.Row
import model.panels.singlestat.SingleStat
import model.panels.stat.Stat
import model.panels.table.Table
import model.panels.timeseries.TimeSeries

import io.circe.Decoder.Result
import io.circe._
import io.circe.generic.extras.Configuration

trait Panel {
  def `type`: String
  def datasource: Option[String]
  def description: Option[String]
  def gridPos: GridPosition
  def id: Option[Int]
  def title: Option[String]

  def asJson: JsonObject
}

object Panel {
  implicit val codecConfig = Configuration.default.withDefaults

  implicit val codec: Codec.AsObject[Panel] =
    new Codec.AsObject[Panel] {
      override def encodeObject(a: Panel): JsonObject = a.asJson.add("type", Json.fromString(a.`type`))

      override def apply(c: HCursor): Result[Panel] = {
        c.downField("type").as[String] match {
          case Right("row")                    => c.as[Row]
          case Right("stat")                   => c.as[Stat]
          case Right("text")                   => c.as[text.Text]
          case Right("grafana-worldmap-panel") => c.as[WorldMapPanel]
          case Right("table")                  => c.as[Table]
          case Right("logs")                   => c.as[Logs]
          case Right("timeseries")             => c.as[TimeSeries]
          case Right("singlestat")             => c.as[SingleStat]
          case Right("graph")                  => c.as[Graph]
          case Right(other)                    => Left(DecodingFailure(s"Unknown panel type '$other'", c.history))
          case Left(_)                         => Left(DecodingFailure("Panel object doesn't contain 'type' field", c.history))
        }
      }
    }
}
