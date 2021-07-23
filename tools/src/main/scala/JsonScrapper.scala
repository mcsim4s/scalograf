package scalograf

import client.GrafanaClient
import model.Dashboard

import com.typesafe.config.ConfigFactory
import io.circe.generic.auto._
import io.circe.syntax._
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future
import scala.util.Failure

object JsonScrapper extends App {
  val log = LoggerFactory.getLogger(this.getClass)
  val conf = ConfigFactory.parseResources("application.local.conf")
  val grafana = conf.getConfig("grafana")

  val client =
    GrafanaClient(grafana.getString("url"), grafana.getString("token"))

  val program = for {
    dashboards <- client.search().map(_.body).orDie
    snippets <- Future.traverse(dashboards) { snippet =>
      client
        .dashboardRaw(snippet.uid)
        .map(_.body)
        .orDie
        .map(_.asObject.get("dashboard").get)
    }
    diff <- Future.traverse(snippets) { json =>
      Future(json.as[Dashboard].map(parsed => JsonAnalyzer.diff(json, parsed.asJson))).orDie
    }
  } yield diff.flatMap(_.map(_.toString())).foreach(log.info)

  program
    .andThen {
      case Failure(exception) => log.error("Fatal", exception)
    }
    .andThen(_ => client.close())

  implicit class RichFutureEither[E <: Throwable, T](val f: Future[Either[E, T]]) {
    def orDie: Future[T] = f.map(_.toTry).flatMap(Future.fromTry)
  }
}
