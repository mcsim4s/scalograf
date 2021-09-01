name := "scalograf"

ThisBuild / scalaVersion := "2.13.6"
ThisBuild / idePackagePrefix := Some("scalograf")
ThisBuild / Test / fork := true
ThisBuild / sonatypeCredentialHost := "s01.oss.sonatype.org"
ThisBuild / skip in publish := true

val sttpVersion = "3.3.11"
val circeVersion = "0.14.1"
val scalatestVersion = "3.2.9"
val enumeratumVersion = "1.7.0"

inThisBuild(
  List(
    organization := "io.github.mcsim4s",
    homepage := Some(url("https://github.com/mcsim4s/scalograf")),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer(
        "mcsim4s",
        "Maxim Gribov",
        "ya.mcsim1993@yandex.ru",
        url("https://github.com/mcsim4s")
      )
    )
  )
)

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
    ),
    skip in publish := false
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
