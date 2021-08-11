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
    cur.asLong.map(CommunityDashboardId.apply)
  val conf = ConfigSource.resources("test.data").loadOrThrow[Conf]

  val container = GrafanaContainer.Def().start()

  val client = GrafanaClient(
    GrafanaConfig(
      Endpoint("http", container.host, container.port),
      LoginPassword("admin", "admin")
    ),
    AsyncHttpClientFutureBackend()
  )

  val program = Future.traverse(conf.communityDashboardIds) { id =>
    for {
      resp <- client.importJson(id)
      body <- Future.fromTry(resp.body.toTry).map(_.asObject).flatMap(fromOption)
      json <- fromOption(body("json"))
      name <- fromOption(body("name"))
      id <- fromOption(body("id").flatMap(_.asNumber).flatMap(_.toLong))
    } yield {
      val testData = JsonObject(
        "name" -> name,
        "json" -> json
      ).asJson
      Files.write(
        Paths.get(s"core/src/test/resources/testDashboards/$id"),
        testData.noSpaces.getBytes(StandardCharsets.UTF_8),
        StandardOpenOption.CREATE
      )
      log.info(s"Written $id with name '$name'")
    }
  }
  program
    .andThen(_ => client.close())
    .andThen(_ => container.stop())
    .failed
    .foreach(err => log.error("Fatal: ", err))
}
