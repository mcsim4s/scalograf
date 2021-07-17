package scalograf

import client.GrafanaClient

import com.typesafe.config.ConfigFactory
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
    _ = log.info(s"Dashboards")
    _ = dashboards.foreach(d => log.info(s"$d"))
    snippets <- Future.traverse(dashboards) { snippet =>
      client.dashboardRaw(snippet.uid).map(_.body).orDie
    }
  } yield snippets.foreach(s => log.info(s"$s"))

  program
    .andThen {
      case Failure(exception) => log.error("Fatal", exception)
    }
    .flatMap(_ => client.close())

  implicit class RichFutureEither[E <: Throwable, T](val f: Future[Either[E, T]]) {
    def orDie: Future[T] = f.map(_.toTry).flatMap(Future.fromTry)
  }
}
