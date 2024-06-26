package scalograf

import client.GrafanaConfig._
import client.notifications.{EmailSettings, NotificationChannel}
import client.{DashboardUploadRequest, GrafanaClient, GrafanaConfig}
import model.Refresh.Every
import model._
import model.alert.{Alert, Notification}
import model.datasource.{Datasource, DatasourceId}
import model.enums.ColorMode.ContinuousBlueYellowRed
import model.enums._
import model.panels.config.FieldConfig.{ThresholdStep, Thresholds}
import model.panels.config.Mappings.Mapping
import model.panels.config.Override.Matcher
import model.panels.config._
import model.panels.row.Row
import model.panels.status_history.{Options, StatusHistory, StatusHistoryConfig}
import model.panels.table.{ColumnAlign, ColumnDisplayMode, Table, TableConfig}
import model.panels.timeseries._
import model.panels.{GridPosition, Panel, ThresholdStyle}
import model.time._
import model.transformations.{Organize, Sort}
import syntax._
import BetterFuture._

import scalograf.model.panels.ThresholdStyle.ThresholdStyleMode
import scalograf.model.panels.barchart.{BarChart, BarChartLabelSpacing, BarChartShowValue}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.implicitConversions

object Demo extends App {

  val env = if (args.isEmpty) Embedded else Standalone(args)

  env.start()
  println(s"Gafana: ${env.grafanaUrl}")

  val client = GrafanaClient(
    GrafanaConfig(env.grafanaUrl, LoginPassword("admin", "admin")),
    OpsBackend()
  )

  val prometheusDatasource = Datasource(
    uid = "prometheus",
    `type` = "prometheus",
    isDefault = true,
    url = "http://prometheus:9090",
    access = "proxy",
    name = "Random Prometheus"
  )
  val emailNotificationChannel = NotificationChannel(
    uid = Some("email-uid"),
    name = "default channel",
    settings = EmailSettings(
      addresses = List("example@example.com")
    )
  )

  val alert = Alert(
    conditions =
      max("A").over(5.seconds).from(now).isAbove(16)
        or avg("A").over(10.minutes).from(now).isAbove(13)
        or min("A").over(1.hour).from(now).isAbove(8),
    name = "High rpc alert",
    frequency = 5.seconds,
    `for` = 10.seconds,
    notifications = emailNotificationChannel.uid.map(uid => Notification.apply(uid)).toList
  )
  val timeSeries = Panel(
    gridPos = GridPosition(12, 12),
    title = "Rps by service",
    id = 1,
    datasource = DatasourceId.Typed(prometheusDatasource.`type`, prometheusDatasource.uid.get),
    typed = TimeSeries(
      alert = alert,
      targets = List(
        Target(
          expr = "sum(irate(grafana_http_request_duration_seconds_count[1m])) by (service)",
          refId = "A",
          legendFormat = "{{service}}",
          intervalFactor = 2
        )
      ),
      fieldConfig = Config[TimeSeriesConfig](
        defaults = FieldConfig[TimeSeriesConfig](
          custom = TimeSeriesConfig(
            lineWidth = 2,
            showPoints = ShowPoints.Never,
            lineInterpolation = LineInterpolation.Smooth,
            thresholdsStyle = ThresholdStyle(ThresholdStyleMode.Line)
          ),
          thresholds = Thresholds(
            mode = ThresholdMode.Absolute,
            steps = List(
              ThresholdStep(Color.Named("green"), 0d),
              ThresholdStep(Color.Named("yellow"), 12d),
              ThresholdStep(Color.Named("red"), 16d)
            )
          )
        )
      )
    )
  )
  val table = Panel(
    gridPos = GridPosition(12, 12),
    title = "Quantiles",
    datasource = DatasourceId.Typed(prometheusDatasource.`type`, prometheusDatasource.uid.get),
    typed = Table(
      targets = List(
        Target(
          expr = "grafana_http_request_duration_seconds_bucket",
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
              custom = TableConfig(
                color = ColorConfig(mode = ContinuousBlueYellowRed),
                displayMode = ColumnDisplayMode.GradientGauge
              ),
              decimals = 1,
              unit = Units.Seconds
            )
          ),
          Override[TableConfig](
            Matcher("byName", "service"),
            properties = FieldConfig[TableConfig](
              custom = TableConfig(
                width = 200
              )
            )
          ),
          Override[TableConfig](
            Matcher("byName", "quantile"),
            properties = FieldConfig[TableConfig](
              custom = TableConfig(
                width = 100
              ),
              mappings = List(
                Mappings.OptionsMapping(
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
        ),
        defaults = FieldConfig[TableConfig](
          thresholds = Thresholds(steps =
            List(
              ThresholdStep(color = Color.Named("green"), value = 0.0003),
              ThresholdStep(color = Color.Named("red"), value = 0.0003)
            )
          ),
          color = ColorConfig(mode = ColorMode.ContinuousBlueYellowRed),
          custom = TableConfig(align = ColumnAlign.Center)
        )
      )
    )
  )
  val statusHistory = Panel(
    gridPos = GridPosition(8, 8, 0),
    title = "Heap size",
    typed = StatusHistory(
      targets = List(
        Target(
          expr =
            "(sum(go_memstats_heap_alloc_bytes) by (job)) / on(job) ((sum(go_memstats_heap_alloc_bytes) by (job)) + on(job) (sum(go_memstats_heap_idle_bytes) by(job)))",
          refId = "A",
          legendFormat = "{{job}}",
          intervalFactor = 3
        )
      ),
      fieldConfig = Config[StatusHistoryConfig](
        FieldConfig(
          color = ColorConfig(mode = ColorMode.ContinuousBlueYellowRed),
          min = 0d,
          max = 0.6,
          unit = Units.PercentUnit
        )
      ),
      options = Options(
        rowHeight = 0.9
      )
    )
  )
  val row = Panel(
    title = "Some test title",
    gridPos = GridPosition(24, 1, 0, 13),
    typed = Row(
      panels = List(statusHistory)
    )
  )

  val barchart = Panel(
    title = "Barchart",
    gridPos = GridPosition(12, 12),
    typed = BarChart(
      targets = List(
        Target(
          expr = """sum by (slice) (increase(prometheus_engine_query_duration_seconds_count[10m]))""",
          refId = "A"
        )
      ),
      interval = "10m",
      options = model.panels.barchart.Options(
        barRadius = 0.1,
        barWidth = 0.9,
        showValue = BarChartShowValue.Auto,
        stacking = StackingMode.Normal,
        xTickLabelRotation = -45,
        xTickLabelSpacing = BarChartLabelSpacing.Small
      )
    )
  )

  val dashboard = Dashboard(
    title = "Demo Dashboard",
    description = "Test dashboard for library abilities demonstration",
    uid = "demo",
    refresh = Every(5.seconds),
    panels = List(barchart, timeSeries, table, row),
    style = DashboardStyle.Dark,
    timepicker = TimePicker(nowDelay = 1.minute)
  )
  val uploadReq = DashboardUploadRequest(
    dashboard = dashboard,
    overwrite = true
  )

  val program = for {
    _ <- client.createDatasource(prometheusDatasource)
    _ <- client.createNotificationChannel(emailNotificationChannel)
    _ <- client.uploadDashboard(uploadReq)
  } yield ()

  program.andThen { _ =>
    env.stop()
    client.close()
  }

  implicit def instance2some[T](instance: T): Option[T] = Some(instance)

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
