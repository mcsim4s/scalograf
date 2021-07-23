package scalograf

import JsonAnalyzer.JsonDiff
import client._
import model._

import io.circe.generic.auto._
import io.circe.syntax._

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

class JsonScrapper(config: GrafanaConfig, limit: Int = 100) {
  val client = GrafanaClient(config)

  def scrape: Future[Seq[JsonDiff]] = {
    val result = for {
      dashboards <- client.search().map(_.body).orDie
      diff <- fillDiff(dashboards)
    } yield diff
    result.andThen(_ => client.close())
  }

  private def fillDiff(dashboards: Seq[DashboardSnippet]): Future[Seq[JsonDiff]] = {
    def inner(acc: Seq[JsonDiff], left: Seq[DashboardSnippet]): Future[Seq[JsonDiff]] = {
      if (acc.length >= limit || left.isEmpty) Future.successful(acc)
      else {
        client
          .dashboardRaw(left.head.uid)
          .map(_.body)
          .orDie
          .map(_.asObject.get("dashboard").get)
          .flatMap(json => Future(json.as[Dashboard].map(parsed => JsonAnalyzer.diff(json, parsed.asJson))).orDie)
          .flatMap(chunk => inner(acc ++ chunk, left.drop(1)))
      }
    }
    inner(Seq.empty, dashboards)
  }

  implicit class RichFutureEither[E <: Throwable, T](val f: Future[Either[E, T]]) {
    def orDie: Future[T] = f.map(_.toTry).flatMap(Future.fromTry)
  }
}
