package scalograf

import client.GrafanaClient

import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits._

object JsonScrapper extends App {
  val log = LoggerFactory.getLogger(this.getClass)
  val conf = ConfigFactory.parseResources("application.local.conf")
  val grafana = conf.getConfig("grafana")

  val client = GrafanaClient(grafana.getString("url"), grafana.getString("token"))

  client.search().transform(
    resp =>log.info(s"${resp.body}"),
    err => {
      log.error("Error", err)
      err
    }
  ).flatMap(_ => client.close())
}
