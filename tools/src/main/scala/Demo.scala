package scalograf

import client.GrafanaConfig._
import client.{GrafanaClient, GrafanaConfig}
import model.datasource.Datasource

import sttp.client3.asynchttpclient.future.AsyncHttpClientFutureBackend

import scala.concurrent.ExecutionContext.Implicits.global

object Demo extends App {

  val env = if (args.isEmpty) Embedded else Standalone(args)

  env.start()

  val client = GrafanaClient(
    GrafanaConfig(env.grafanaUrl, LoginPassword("admin", "admin")),
    AsyncHttpClientFutureBackend()
  )
  val prometheusDatasource = Datasource(
    uid = Some("prometheus"),
    `type` = "prometheus",
    isDefault = true,
    url = "http://prometheus:9090",
    access = "server"
  )

  client
    .create(prometheusDatasource)
    .andThen(_ => env.stop())

  trait Env {
    def start(): Unit
    def stop(): Unit
    def grafanaUrl: String
  }

  object Embedded extends Env {
    val env = DataSourcesEnv("testkit/src/main/resources/datasources/docker-compose.yaml")
    override def start(): Unit = {
      env.start()

      sys.addShutdownHook {
        env.stop()
      }

      println(s"Gafana: ${env.grafanaUrl}")
      println(s"Prometheus: ${env.prometheusUrl}")
    }

    override def stop(): Unit = {
      scala.io.StdIn.readLine()
      env.stop()
      println("Stopped")
    }

    lazy val grafanaUrl: String = env.grafanaUrl
  }
  case class Standalone(args: Array[String]) extends Env {
    override def start(): Unit = ()

    override def stop(): Unit = ()

    override def grafanaUrl: String = args.head
  }
}
