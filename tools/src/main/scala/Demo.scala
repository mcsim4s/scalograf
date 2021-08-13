package scalograf

import client.GrafanaConfig._
import client.{DashboardUploadRequest, GrafanaClient, GrafanaConfig}
import model.Refresh.Every
import model.datasource.Datasource
import model.enums.{ColorMode, DashboardStyle, TargetFormat}
import model.panels.GridPosition
import model.panels.config.{ColorConfig, Config, FieldConfig, Override}
import model.panels.table.{Table, TableConfig}
import model.panels.timeseries.{ShowPoints, TimeSeries, TimeSeriesConfig}
import model.transformations.{Organize, Sort}
import model.{Color, Dashboard, Mappings, Target, TimePicker}

import scalograf.model.Mappings.Mapping
import scalograf.model.enums.ColorMode.ContinuousBlueYellowRed
import scalograf.model.panels.config.FieldConfig.{ThresholdStep, Thresholds}
import scalograf.model.panels.config.Override.Matcher

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
    gridPos = GridPosition(12, 12, 0, 0),
    title = Some("Rps by service"),
    datasource = Some(prometheusDatasource.name),
    targets = List(
      Target(
        expr = "sum(irate(rpc_durations_seconds_count[1m])) by (service)",
        refId = "A",
        legendFormat = "{{service}}"
      )
    ),
    fieldConfig = Config[TimeSeriesConfig](
      defaults = FieldConfig[TimeSeriesConfig](
        custom = Some(
          TimeSeriesConfig(
            lineWidth = Some(2),
            showPoints = Some(ShowPoints.Never)
          )
        )
      )
    )
  )
  val table = Table(
    gridPos = GridPosition(12, 12, 12, 0),
    title = Some("Quantiles"),
    datasource = Some(prometheusDatasource.name),
    targets = List(
      Target(
        expr = "rpc_durations_seconds",
        refId = "A",
        instant = true,
        legendFormat = "{{service}}",
        format = TargetFormat.Table
      )
    ),
    transformations = List(
      Organize(
        excludeByName = List("Time", "__name__", "instance", "job").map(_ -> true).toMap,
        indexByName = List("service", "quantile", "Value").zipWithIndex.toMap
      ),
      Sort(sort = List(Sort.By("service")))
    ),
    fieldConfig = Config[TableConfig](
      overrides = List(
        Override[TableConfig](
          Matcher("byName", "Value"),
          properties = FieldConfig[TableConfig](
            custom = Some(
              TableConfig(
                color = Some(ColorConfig(mode = ContinuousBlueYellowRed)),
                displayMode = Some("gradient-gauge")
              )
            ),
            decimals = Some(1),
            unit = Some("s")
          )
        ),
        Override[TableConfig](
          Matcher("byName", "service"),
          properties = FieldConfig[TableConfig](
            custom = Some(
              TableConfig(
                width = Some(200)
              )
            )
          )
        ),
        Override[TableConfig](
          Matcher("byName", "quantile"),
          properties = FieldConfig[TableConfig](
            custom = Some(
              TableConfig(
                width = Some(100)
              )
            ),
            mappings = Some(
              List(
                Mappings(
                  `type` = "value",
                  options = Map(
                    "0.5" -> Mapping(1, "50%"),
                    "0.9" -> Mapping(2, "90%"),
                    "0.99" -> Mapping(3, "99%")
                  )
                )
              )
            )
          )
        )
      ),
      defaults = FieldConfig[TableConfig](
        thresholds = Some(
          Thresholds(steps =
            List(
              ThresholdStep(color = Color.Named("green"), value = 0.0003),
              ThresholdStep(color = Color.Named("red"), value = 0.0003)
            )
          )
        ),
        color = Some(ColorConfig(mode = ColorMode.ContinuousBlueYellowRed)),
        custom = Some(TableConfig(align = Some("center")))
      )
    )
  )

  val dashboard = Dashboard(
    title = "Demo Dashboard",
    description = Some("Test dashboard for library abilities demonstration"),
    uid = Some("demo"),
    refresh = Every("5s"),
    panels = List(timeSeries, table),
    style = DashboardStyle.Dark,
    timepicker = TimePicker(nowDelay = Some("1m"))
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
