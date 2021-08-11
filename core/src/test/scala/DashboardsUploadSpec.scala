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
        val client = GrafanaClient(
          GrafanaConfig(
            Scheme("http", container.host, container.port),
            LoginPassword("admin", "admin")
          ),
          AsyncHttpClientFutureBackend()
        )

        Future
          .traverse(testDataDir.listFiles().toList) { dashboardFile =>
            val json = parse(Source.fromFile(dashboardFile).getLines().mkString) match {
              case Left(value)  => throw value
              case Right(value) => value
            }
            val dashboardJson = json.asObject.get("json").get
            val dashboard = dashboardJson.as[Dashboard] match {
              case Left(value)  => throw value
              case Right(value) => value
            }

            client
              .upload(DashboardUploadRequest(dashboard))
              .map(_.body)
              .map(println)
          }
          .map(_ => assert(true))
    }
  }
}
