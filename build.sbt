name := "scalograf"

ThisBuild / version := "0.1"
ThisBuild / scalaVersion := "2.13.6"
ThisBuild / idePackagePrefix := Some("scalograf")

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
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion
    )
  )

val tools = (project in file("tools"))
  .settings(
    name := "tools",
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.4.1",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "org.scalatest" %% "scalatest" % scalatestVersion % "test"
    )
  )
  .dependsOn(core)
