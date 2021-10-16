package scalograf

import client.GrafanaConfig.{NoAuth, Url}
import client.{GrafanaClient, GrafanaConfig}

import io.circe.{Decoder, Json}
import scalograf.model.CommunityDashboardId
import sttp.client3.{IsOption, UriContext}
import sttp.client3.circe.{asJson, asJsonAlways}
import sttp.client3.httpclient.zio.HttpClientZioBackend
import zio.{Has, Task, TaskLayer}

object LiveGrafanaClient {
  def make: Task[GrafanaClient[Task]] =
    HttpClientZioBackend().map { backend =>
      GrafanaClient(GrafanaConfig(Url("https://grafana.com"), NoAuth), backend)
    }

  val layer: TaskLayer[Has[GrafanaClient[Task]]] = make.toLayer

  implicit class RichGrafanaClient(val client: GrafanaClient[Task]) {
    // https://grafana.com/api/dashboards?orderBy=downloads&direction=desc&includeLogo=1&page=1&pageSize=100
    def dashboardsSearch(page: Int) = {
      client.grafanaRequest
        .get(
          uri = uri"${client.url}/api/dashboards"
            .addParam("orderBy", "downloads")
            .addParam("direction", "desc")
            .addParam("pageSize", "100")
            .addParam("page", page.toString)
        )
        .response(asJsonAlways[List[ScrapeTask]](dashboardsSearchDecoder, IsOption.otherIsNotOption))
        .send(client.backend)
    }
  }

  implicit val scrapeTaskDecoder = Decoder.instance[ScrapeTask] { c =>
    for {
      id <- c.downField("id").as[Long]
      revision <- c.downField("revision").as[Long]
      name <- c.downField("name").as[String]
    } yield ScrapeTask(CommunityDashboardId(id, revision), name, None)
  }

  val dashboardsSearchDecoder: Decoder[List[ScrapeTask]] =
    Decoder.instance[List[ScrapeTask]](_.downField("items").as[List[ScrapeTask]])
}
