package scalograf
import zio._

object App extends zio.App {
  val env = zio.ZEnv.any >+> AppConf.live >+> TaskDao.live

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    program
      .provideLayer(env)
      .exitCode

  private val program = Logic.scrape
}
