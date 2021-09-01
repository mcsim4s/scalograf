package scalograf

import doobie.implicits._
import doobie.util.transactor.Transactor
import zio._
import zio.blocking.Blocking
import zio.clock.Clock
import zio.interop.catz._

class TaskDao(tx: Transactor[Task]) {
  def pull(limit: Int): Task[List[ScrapeTask]] =
    sql"SELECT id, name from tasks WHERE scrapper IS NULL LIMIT $limit".query[ScrapeTask].to[List].transact(tx)

  def tag(id: Long, version: String) =
    sql"""UPDATE tasks SET scrapper = $version WHERE id = $id""".update.run.transact(tx).unit

  def untagAll(butVersion: String): Task[Unit] =
    sql"""UPDATE tasks SET scrapper = NULL WHERE scrapper <> $butVersion""".update.run.transact(tx).unit

  def init(): Task[Unit] =
    sql"""CREATE TABLE IF NOT EXISTS tasks
         |(
         |	id bigint NOT NULL
         |		constraint tasks_pk
         |			primary key,
         |	name text NOT NULL,
         |	scrapper text
         |)
         |""".stripMargin.update.run.transact(tx).unit
}

object TaskDao {

  def pull(limit: Int): ZIO[Has[TaskDao], Throwable, List[ScrapeTask]] = ZIO.service[TaskDao].flatMap(_.pull(limit))
  def tag(id: Long, tag: String): RIO[Has[TaskDao], Unit] = ZIO.service[TaskDao].flatMap(_.tag(id, tag))
  def untagAll(butVersion: String): RIO[Has[TaskDao], Unit] = ZIO.service[TaskDao].flatMap(_.untagAll(butVersion))

  def makeTransactor: URIO[Clock with Blocking with Has[AppConf], Transactor[Task]] =
    for {
      conf <- ZIO.service[AppConf]
      runtime <- ZIO.runtime[Clock with Blocking]
    } yield Transactor.fromDriverManager[Task](
      "org.postgresql.Driver",
      conf.db.url,
      conf.db.user,
      conf.db.password
    )(asyncRuntimeInstance(runtime))

  def make: RIO[Clock with Blocking with Has[AppConf], TaskDao] =
    makeTransactor
      .map(new TaskDao(_))
      .tap(_.init())

  val live: RLayer[Clock with Blocking with Has[AppConf], Has[TaskDao]] = make.toLayer
}
