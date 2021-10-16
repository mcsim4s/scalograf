package scalograf

import BetterFuture._
import client.GrafanaConfig._
import client._
import model.CommunityDashboardId

import io.circe.JsonObject
import io.circe.syntax._
import org.slf4j.LoggerFactory
import pureconfig.generic.auto._
import pureconfig.{ConfigCursor, ConfigReader, ConfigSource}
import sttp.client3.asynchttpclient.future.AsyncHttpClientFutureBackend

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths, StandardOpenOption}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object TestDataGen extends App {
  private val log = LoggerFactory.getLogger(this.getClass)
  case class Conf(communityDashboardIds: List[CommunityDashboardId])
  implicit val DashboardIdReader: ConfigReader[CommunityDashboardId] = (cur: ConfigCursor) =>
    cur.asListCursor.flatMap { elems =>
      for {
        id <- elems.atIndexOrUndefined(0).asLong
        revision <- elems.atIndexOrUndefined(1).asLong
      } yield CommunityDashboardId(id, revision)

    }
  val conf = ConfigSource.resources("test.data").loadOrThrow[Conf]

  val client = GrafanaClient(
    GrafanaConfig(Url("https://grafana.com"), NoAuth),
    AsyncHttpClientFutureBackend()
  )

  val program = Future.traverse(conf.communityDashboardIds) { id =>
    for {
      resp <- client.importJson(id)
      json <- Future.fromTry(resp.body.toTry)
    } yield {
      Files.write(
        Paths.get(s"core/src/test/resources/testDashboards/${id.id}.json"),
        json.spaces2.getBytes(StandardCharsets.UTF_8),
        StandardOpenOption.CREATE
      )
      log.info(s"Written $id'")
    }
  }
  program
    .andThen(_ => client.close())
    .failed
    .foreach(err => log.error("Fatal: ", err))
}
