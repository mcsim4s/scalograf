package scalograf

import client.GrafanaConfig._
import client.{DashboardUploadRequest, GrafanaClient, GrafanaConfig}
import model.Refresh.Every
import model.datasource.Datasource
import model.panels.FieldConfig.Defaults
import model.panels.timeseries.{ShowPoints, TimeSeries, TimeSeriesConfig}
import model.panels.{FieldConfig, GridPosition}
import model.{Dashboard, Target}

import scala.concurrent.ExecutionContext.Implicits.global

object Demo extends App {

  val env = if (args.isEmpty) Embedded else Standalone(args)

  env.start()
  println(s"Gafana: ${env.grafanaUrl}")

  val client = GrafanaClient(
    GrafanaConfig(env.grafanaUrl, LoginPassword("admin", "admin")),
    OpsBackend()
  )
  val prometheusDatasource = Datasource(
    uid = Some("prometheus"),
    `type` = "prometheus",
    isDefault = true,
    url = "http://prometheus:9090",
    access = "proxy",
    name = "Random Prometheus"
  )

  val timeSeries = TimeSeries(
    gridPos = GridPosition(12, 10, 0, 0),
    title = Some("Rps by service"),
    datasource = Some(prometheusDatasource.name),
    targets = List(
      Target(
        expr = "sum(irate(rpc_durations_seconds_count[1m])) by (service)",
        refId = "A",
        legendFormat = "{{service}}"
      )
    ),
    fieldConfig = FieldConfig[TimeSeriesConfig](
      defaults = Defaults[TimeSeriesConfig](
        custom = Some(
          TimeSeriesConfig(
            lineWidth = 2,
            showPoints = ShowPoints.Never
          )
        )
      )
    )
  )

  val dashboard = Dashboard(
    title = "Demo Dashboard",
    description = Some("Test dashboard for library abilities demonstration"),
    uid = Some("demo"),
    refresh = Every("5s"),
    panels = List(timeSeries)
  )
  val uploadReq = DashboardUploadRequest(
    dashboard = dashboard,
    overwrite = true
  )

  val program = for {
    _ <- client.createDatasource(prometheusDatasource)
    _ <- client.uploadDashboard(uploadReq)
  } yield ()

  program.andThen { _ =>
    client.close()
    env.stop()
  }

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
