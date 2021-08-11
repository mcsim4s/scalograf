package scalograf

object Demo extends App {
  val env = DataSourcesEnv("testkit/src/main/resources/datasources/docker-compose.yaml")

  env.start()

  println(s"Gafana: ${env.grafanaUrl}")
  println(s"Prometheus: ${env.prometheusUrl}")

  scala.io.StdIn.readLine()

  env.stop()
  println("Stopped")
}
