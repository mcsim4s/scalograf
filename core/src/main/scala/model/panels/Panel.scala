package scalograf
package model.panels

import model.panels.graph.Graph
import model.panels.logs.Logs
import model.panels.map.WorldMapPanel
import model.panels.row.Row
import model.panels.singlestat.SingleStat
import model.panels.stat.Stat
import model.panels.status_history.StatusHistory
import model.panels.table.Table
import model.panels.timeseries.TimeSeries

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.syntax._

case class Panel(
    datasource: Option[String] = None,
    description: Option[String] = None,
    gridPos: GridPosition,
    id: Option[Int] = None,
    title: Option[String] = None,
    typed: Panel.Type
) {
  def withPos(pos: GridPosition) = copy(gridPos = pos) //ToDo lenses???
}

object Panel {
  trait Type {
    def `type`: String
    def asJson: JsonObject
  }

  implicit val codecConfig = Configuration.default.withDefaults

  implicit val encoder: Encoder.AsObject[Panel] =
    Encoder.AsObject.instance { panel =>
      JsonObject(
        "datasource" -> panel.datasource.asJson,
        "description" -> panel.description.asJson,
        "gridPos" -> panel.gridPos.asJson,
        "id" -> panel.id.asJson,
        "title" -> panel.title.asJson,
        "type" -> panel.typed.`type`.asJson
      ).deepMerge(panel.typed.asJson)
    }

  implicit val decoder: Decoder[Panel] =
    Decoder.instance[Panel] { c =>
      c.downField("type")
        .as[String]
        .flatMap {
          case "row"                    => c.as[Row]
          case "stat"                   => c.as[Stat]
          case "text"                   => c.as[text.Text]
          case "grafana-worldmap-panel" => c.as[WorldMapPanel]
          case "table"                  => c.as[Table]
          case "logs"                   => c.as[Logs]
          case "timeseries"             => c.as[TimeSeries]
          case "singlestat"             => c.as[SingleStat]
          case "graph"                  => c.as[Graph]
          case "status-history"         => c.as[StatusHistory]
          case other                    => Left(DecodingFailure(s"Unknown panel type '$other'", c.history))
        }
        .flatMap { typed =>
          for {
            datasource <- c.downField("datasource").as[Option[String]]
            description <- c.downField("description").as[Option[String]]
            gridPos <- c.downField("gridPos").as[GridPosition]
            id <- c.downField("id").as[Option[Int]]
            title <- c.downField("title").as[Option[String]]
          } yield Panel(
            datasource = datasource,
            description = description,
            gridPos = gridPos,
            id = id,
            title = title,
            typed = typed
          )
        }
    }
}
