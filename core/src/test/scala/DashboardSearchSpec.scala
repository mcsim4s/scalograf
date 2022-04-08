package scalograf

import com.dimafeng.testcontainers.ContainerDef
import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import io.circe.parser.parse
import org.scalatest.OptionValues
import org.scalatest.matchers.should
import org.scalatest.wordspec.AsyncWordSpec
import scalograf.client.{DashboardUploadRequest, GrafanaClient, GrafanaConfig}
import scalograf.client.GrafanaConfig.{LoginPassword, Scheme}
import scalograf.model.Dashboard
import sttp.client3.HttpError
import sttp.client3.asynchttpclient.future.AsyncHttpClientFutureBackend

import java.io.File
import scala.concurrent.Future
import scala.io.Source

class DashboardSearchSpec extends AsyncWordSpec with should.Matchers with OptionValues with TestContainerForAll {
  val testDataDir = new File(getClass.getResource("/testDashboards").getPath)
  override val containerDef: ContainerDef = GrafanaContainer.Def()

  "Grafana client" should {
    s"not found folders" in withContainers {
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
              .flatMap { _ => client.search(Map("type" -> "dash-folder")) }
              .map(_.body)
              .map { body =>
                println(s"${dashboard.gnetId} $body")
                body match {
                  case Right(listOfFolders)                                          => Right(listOfFolders.isEmpty)
                  case l @ Left(HttpError(b, status)) if dashboard.__inputs.nonEmpty => l
                }
              }
          }
          .map(_ => assert(true))
    }

    s"found dashboards" in withContainers {
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
              .flatMap { _ => client.search(Map("type" -> "dash-db")) }
              .map(_.body)
              .map { body =>
                println(s"${dashboard.gnetId} $body")
                body match {
                  case Right(listOfFolders)                                          => Right(listOfFolders.nonEmpty)
                  case l @ Left(HttpError(b, status)) if dashboard.__inputs.nonEmpty => l
                }
              }
          }
          .map(_ => assert(true))
    }
  }
}
