package scalograf

import client.GrafanaConfig

import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits._
import scala.util.{Failure, Success}

object MainTest extends App {
  val log = LoggerFactory.getLogger(this.getClass)
  val conf = ConfigFactory.parseResources("application.local.conf").getConfig("grafana")
  val scrapper = new JsonScrapper(GrafanaConfig(conf.getString("url"), conf.getString("token")))

  scrapper.scrape
    .onComplete {
      case Failure(err)  => log.error("Fatal", err)
      case Success(diff) => diff.map(_.toString()).foreach(log.info)
    }
}
