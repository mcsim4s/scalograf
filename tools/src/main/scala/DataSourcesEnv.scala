package scalograf

import com.dimafeng.testcontainers.{DockerComposeContainer, ExposedService}
import com.dimafeng.testcontainers.DockerComposeContainer._

import java.io.File

case class DataSourcesEnv(composeFile: String)
    extends DockerComposeContainer(
      new File(composeFile),
      Seq(ExposedService("grafana", 3000), ExposedService("prometheus", 9090))
    ) {
  lazy val grafanaUrl = s"http://${getServiceHost("grafana", 3000)}:${getServicePort("grafana", 3000)}"
  lazy val prometheusUrl = s"http://${getServiceHost("prometheus", 9090)}:${getServicePort("prometheus", 9090)}"
}
