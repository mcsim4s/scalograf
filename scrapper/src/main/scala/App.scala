package scalograf
import scalograf.client.GrafanaClient
import zio._

object App extends zio.App {
  type Env = zio.ZEnv with Has[TaskDao] with Has[AppConf] with Has[GrafanaClient[Task]]

  val env: ZLayer[zio.ZEnv, Throwable, Env] = zio.ZEnv.any >+> AppConf.live >+> TaskDao.live >+> LiveGrafanaClient.layer

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    program
      .provideLayer(env)
      .exitCode

  private val program = Logic.scrape
}
