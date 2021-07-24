name := "scalograf"

ThisBuild / version := "0.1"
ThisBuild / scalaVersion := "2.13.6"
ThisBuild / idePackagePrefix := Some("scalograf")
ThisBuild / Test / fork := true

val sttpVersion = "3.3.11"
val circeVersion = "0.14.1"

val scalatestVersion = "3.2.9"

val core = (project in file("core"))
  .settings(
    name := "core",
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %% "core" % sttpVersion,
      "com.softwaremill.sttp.client3" %% "async-http-client-backend-future" % sttpVersion,
      "com.softwaremill.sttp.client3" %% "circe" % sttpVersion,
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "io.circe" %% "circe-generic-extras" % circeVersion
    )
  )

val tools = (project in file("tools"))
  .settings(
    name := "tools",
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.github.pureconfig" %% "pureconfig" % "0.16.0",
      "org.testcontainers" % "testcontainers" % "1.15.3" % "test",
      "com.dimafeng" %% "testcontainers-scala-scalatest" % "0.39.5",
      "org.scalatest" %% "scalatest" % scalatestVersion % "test"
    )
  )
  .dependsOn(core)
