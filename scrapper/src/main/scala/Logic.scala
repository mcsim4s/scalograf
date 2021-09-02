package scalograf

import App.Env
import LiveGrafanaClient._
import client.GrafanaClient
import model.{CommunityDashboardId, Dashboard}

import io.circe.syntax._
import zio._
import zio.console.Console
import zio.stream.ZStream

object Logic {

  lazy val version = ZIO.service[AppConf].map(_.version)

  lazy val untagOldScrapping = version.flatMap(TaskDao.untagAll)

  lazy val existingDashBoards: ZStream[Has[TaskDao], Throwable, ScrapeTask] = ZStream.repeatEffectChunkOption {
    TaskDao.pull(100).mapBoth(Some.apply, Chunk.fromIterable).tap(r => ZIO.fail(None).when(r.isEmpty))
  }

  lazy val newDashboards: ZStream[Console with Has[GrafanaClient[Task]], Throwable, ScrapeTask] =
    ZStream.unfoldChunkM(0) { pageNum =>
      for {
        client <- ZIO.service[GrafanaClient[Task]]
        results <- client.dashboardsSearch(pageNum).flatMap(r => ZIO.fromEither(r.body))
      } yield Some(Chunk.fromIterable(results), pageNum + 1)
    }

  lazy val scrape: ZIO[Env, Throwable, Unit] =
    untagOldScrapping *>
      existingDashBoards
        .concat(newDashboards)
        .foreach(process)

  def process(task: ScrapeTask): ZIO[Env, Throwable, Unit] =
    for {
      v <- version
      client <- ZIO.service[GrafanaClient[Task]]
      resp <- client.importJson(task.id)
      json <- ZIO.fromEither(resp.body).orDie
      _ <- json.as[Dashboard] match {
        case Left(error) => TaskDao.addError(SchemeError(task.id, v, "parse", error.getMessage()))
        case Right(dashboard) =>
          val diff = JsonAnalyzer.diff(json, dashboard.asJson)
          val errors = diff.map(e => SchemeError(task.id, v, e.getClass.getSimpleName, e.toString))
          ZIO.foreach(errors)(TaskDao.addError)
      }
      _ <- TaskDao.tag(task.copy(scrapedWith = Some(v)))
      _ <- zio.console.putStrLn(s"Processed $task")
    } yield ()
}
