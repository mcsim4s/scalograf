package scalograf

import doobie.implicits._
import doobie.util.transactor.Transactor
import zio._
import zio.blocking.Blocking
import zio.clock.Clock
import zio.interop.catz._

class TaskDao(tx: Transactor[Task]) {
  def pull(limit: Int): Task[List[ScrapeTask]] =
    sql"SELECT id, revision, name, scrapper from tasks WHERE scrapper IS NULL LIMIT $limit"
      .query[ScrapeTask]
      .to[List]
      .transact(tx)

  def tag(task: ScrapeTask) =
    sql"""INSERT INTO tasks (id, revision, name, scrapper)
          VALUES (${task.id.id}, ${task.id.revision}, ${task.name}, ${task.scrapedWith})
          ON CONFLICT ON CONSTRAINT tasks_pk DO 
            UPDATE SET scrapper = ${task.scrapedWith} WHERE tasks.id = ${task.id.id}
         """.update.run.transact(tx).unit

  def untagAll(butVersion: String): Task[Unit] =
    sql"""UPDATE tasks SET scrapper = NULL WHERE scrapper <> $butVersion""".update.run.transact(tx).unit

  def addError(err: SchemeError) =
    sql"""INSERT INTO errors (dashboard_id, dashboard_revision, scrapped_with, error_type, message)
          VALUES (
            ${err.dashboardId.id},
            ${err.dashboardId.revision}, 
            ${err.scrapedWith}, 
            ${err.`type`}, 
            ${err.message}
          )
          ON CONFLICT ON CONSTRAINT errors_pk DO NOTHING """.stripMargin.update.run.transact(tx).unit

  def initTasks(): Task[Unit] =
    sql"""CREATE TABLE IF NOT EXISTS tasks
         |(
         |	id bigint NOT NULL
         |		constraint tasks_pk
         |			primary key,
         |  revision bigint,
         |	name text NOT NULL,
         |	scrapper text
         |)
         |""".stripMargin.update.run.transact(tx).unit

  def initErrors(): Task[Unit] =
    sql"""create table IF NOT EXISTS errors
         |(
         |	dashboard_id bigint,
         |	dashboard_revision bigint,
         |	scrapped_with text,
         |	error_type text,
         |	message text,
         |	constraint errors_pk
         |		primary key (dashboard_id, dashboard_revision, scrapped_with)
         |)
         |""".stripMargin.update.run.transact(tx).unit

  def init() = initTasks() *> initErrors()
}

object TaskDao {

  def pull(limit: Int): ZIO[Has[TaskDao], Throwable, List[ScrapeTask]] = ZIO.service[TaskDao].flatMap(_.pull(limit))
  def tag(task: ScrapeTask): RIO[Has[TaskDao], Unit] = ZIO.service[TaskDao].flatMap(_.tag(task))
  def untagAll(butVersion: String): RIO[Has[TaskDao], Unit] = ZIO.service[TaskDao].flatMap(_.untagAll(butVersion))
  def addError(err: SchemeError): RIO[Has[TaskDao], Unit] = ZIO.service[TaskDao].flatMap(_.addError(err))

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
