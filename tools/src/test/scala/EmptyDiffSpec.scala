package scalograf

import client._

import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should
import org.scalatest.wordspec.AsyncWordSpec
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

class EmptyDiffSpec extends AsyncWordSpec with should.Matchers with BeforeAndAfterAll {
  val grafana = new GenericContainer(DockerImageName.parse("grafana/grafana"))
  grafana.withExposedPorts(3000)

  override protected def beforeAll(): Unit = {
    grafana.start()
  }

  override protected def afterAll(): Unit = {
    grafana.stop()
  }

  "Model diff analyzer" should {
    "build empty diff" in {
      val scrapper = new JsonScrapper(
        GrafanaConfig(
          Endpoint("http", grafana.getHost, grafana.getMappedPort(3000).toLong),
          Auth("admin", "admin")
        )
      )
      for {
        diff <- scrapper.scrape
      } yield {
        diff should be(empty)
      }
    }
  }

}
