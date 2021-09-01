name := "scalograf"

ThisBuild / scalaVersion := "2.13.6"
ThisBuild / idePackagePrefix.withRank(KeyRanks.Invisible) := Some("scalograf")
ThisBuild / Test / fork := true

val sttpVersion = "3.3.13"
val circeVersion = "0.14.1"
val scalatestVersion = "3.2.9"
val enumeratumVersion = "1.7.0"
val zioVersion = "1.0.11"
val doobieVersion = "1.0.0-RC1"

val testkit = (project in file("testkit"))
  .settings(
    name := "testkit",
    libraryDependencies ++= Seq(
      "com.dimafeng" %% "testcontainers-scala-scalatest" % "0.39.5",
      "org.testcontainers" % "testcontainers" % "1.15.3",
      "io.circe" %% "circe-core" % circeVersion,
      "org.scalatest" %% "scalatest" % scalatestVersion % "test",
      "io.circe" %% "circe-parser" % circeVersion % "test"
    )
  )

val core = (project in file("core"))
  .settings(
    name := "core",
    libraryDependencies ++= Seq(
      "com.beachape" %% "enumeratum" % enumeratumVersion,
      "com.beachape" %% "enumeratum-circe" % enumeratumVersion,
      "com.softwaremill.sttp.client3" %% "circe" % sttpVersion,
      "com.softwaremill.sttp.client3" %% "core" % sttpVersion,
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic-extras" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "com.softwaremill.sttp.client3" %% "async-http-client-backend-future" % sttpVersion % "test",
      "org.scalatest" %% "scalatest" % scalatestVersion % "test",
      "ch.qos.logback" % "logback-classic" % "1.2.3" % "test"
    )
  )
  .dependsOn(testkit % "compile->test;test->test")

val tools = (project in file("tools"))
  .settings(
    name := "tools",
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.github.pureconfig" %% "pureconfig" % "0.16.0",
      "com.softwaremill.sttp.client3" %% "async-http-client-backend-future" % sttpVersion
    )
  )
  .dependsOn(core, testkit)

val scrapper = (project in file("scrapper"))
  .settings(
    name := "tools",
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.github.pureconfig" %% "pureconfig" % "0.16.0",
      "com.softwaremill.sttp.client3" %% "httpclient-backend-zio" % sttpVersion,
      "dev.zio" %% "zio" % zioVersion,
      "dev.zio" %% "zio-interop-cats" % "3.1.1.0",
      "org.tpolecat" %% "doobie-core" % doobieVersion,
      "org.tpolecat" %% "doobie-postgres" % doobieVersion,
      "org.tpolecat" %% "doobie-hikari" % doobieVersion
    )
  )
  .dependsOn(core)
