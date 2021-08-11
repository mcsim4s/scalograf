name := "scalograf"

ThisBuild / version := "0.1"
ThisBuild / scalaVersion := "2.13.6"
ThisBuild / idePackagePrefix := Some("scalograf")
ThisBuild / Test / fork := true

val sttpVersion = "3.3.11"
val circeVersion = "0.14.1"

val scalatestVersion = "3.2.9"

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
  .dependsOn(testkit % "compile->compile;test->test")

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
