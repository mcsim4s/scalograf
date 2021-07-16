package scalograf

import client.GrafanaClient

import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContext.Implicits._

object JsonScrapper extends App {
  val conf = ConfigFactory.parseResources("application.local.conf")
  val grafana = conf.getConfig("grafana")

  val client = GrafanaClient(grafana.getString("url"), grafana.getString("token"))

  client.search().transform(
    resp => println(resp.body),
    err => {
      println("Error", err)
      err
    }
  ).flatMap(_ => client.close())
}
