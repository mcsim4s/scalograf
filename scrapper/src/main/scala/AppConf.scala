package scalograf

import pureconfig.ConfigSource
import pureconfig.error.ConfigReaderFailures
import pureconfig.generic.auto._
import zio.{Has, IO, TaskLayer, ZIO, ZLayer}

case class AppConf(version: String, db: DbConf)
case class DbConf(url: String, user: String, password: String)

object AppConf {
  val make: IO[ConfigReaderFailures, AppConf] = ZIO.fromEither(ConfigSource.default.load[AppConf])

  val live: TaskLayer[Has[AppConf]] = make.toLayer.mapError { confReadError =>
    new IllegalArgumentException(s"Config read error: $confReadError")
  }
}
