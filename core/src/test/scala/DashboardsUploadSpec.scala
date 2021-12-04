package scalograf

import client.GrafanaConfig.{LoginPassword, Scheme}
import client.{DashboardUploadRequest, GrafanaClient, GrafanaConfig}
import model.Dashboard

import com.dimafeng.testcontainers.ContainerDef
import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import io.circe.parser._
import org.scalatest.OptionValues
import org.scalatest.matchers.should
import org.scalatest.wordspec.AsyncWordSpec
import sttp.client3.HttpError
import sttp.client3.asynchttpclient.future.AsyncHttpClientFutureBackend

import java.io.File
import scala.concurrent.Future
import scala.io.Source

class DashboardsUploadSpec extends AsyncWordSpec with should.Matchers with OptionValues with TestContainerForAll {
  val testDataDir = new File(getClass.getResource("/testDashboards").getPath)
  override val containerDef: ContainerDef = GrafanaContainer.Def()

  "Grafana client" should {
    s"upload all dashboards from test data" in withContainers {
      case container: GrafanaContainer =>
        println(container.url)
        val client = GrafanaClient(
          GrafanaConfig(
            Scheme("http", container.host, container.port),
            LoginPassword("admin", "admin")
          ),
          AsyncHttpClientFutureBackend()
        )

        Future
          .traverse(testDataDir.listFiles().toList) { dashboardFile =>
            val dashboardJson = parse(Source.fromFile(dashboardFile).getLines().mkString) match {
              case Left(value)  => throw value
              case Right(value) => value
            }
            val dashboard = dashboardJson.as[Dashboard] match {
              case Left(value)  => throw value
              case Right(value) => value
            }

            client
              .uploadDashboard(DashboardUploadRequest(dashboard))
              .map(_.body)
              .map { body =>
                println(s"${dashboard.gnetId} $body")
                body should matchPattern {
                  case Right(_)                                                  =>
                  case Left(HttpError(_, status)) if dashboard.__inputs.nonEmpty =>
                }
              }
          }
          .map(_ => assert(true))
    }
  }
}
