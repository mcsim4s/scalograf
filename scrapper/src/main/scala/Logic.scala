package scalograf

import zio.console.Console
import zio._
import zio.stream.ZStream

object Logic {

  lazy val version = ZIO.service[AppConf].map(_.version)

  lazy val untagOldScrapping = version.flatMap(TaskDao.untagAll)

  lazy val existingDashBoards = ZStream.repeatEffectChunkOption {
    TaskDao.pull(100).map(Chunk.fromIterable).mapError(Some.apply).tap(r => ZIO.fail(None).when(r.isEmpty))
  }

  def process(task: ScrapeTask) =
    for {
      v <- version
      _ <- console.putStrLn(task.id.toString)
      _ <- TaskDao.tag(task.id, v)
    } yield ()

  lazy val scrape: ZIO[Console with Has[TaskDao] with Has[AppConf], Throwable, Unit] =
    untagOldScrapping *>
      existingDashBoards
        .foreach(process)
}
