package scalograf

import com.dimafeng.testcontainers.GenericContainer

case class GrafanaContainer(delegate: GenericContainer) extends GenericContainer(delegate) {
  def port: Long = this.mappedPort(3000).toLong
  def url: String = s"http://localhost:$port"
}

object GrafanaContainer {
  case class Def()
      extends GenericContainer.Def[GrafanaContainer](
        new GrafanaContainer(
          GenericContainer(
            dockerImage = "grafana/grafana:11.0.0",
            exposedPorts = Seq(3000)
          )
        )
      )
}
