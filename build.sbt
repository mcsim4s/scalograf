name := "scalograf"

ThisBuild / version := "0.1"
ThisBuild / scalaVersion := "2.13.6"
ThisBuild / idePackagePrefix := Some("scalograf")

val sttpVersion = "3.3.11"

val client = (project in file("client"))
  .settings(
    name := "client",
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %% "core" % sttpVersion,
      "com.softwaremill.sttp.client3" %% "async-http-client-backend-future" % sttpVersion,
      "com.softwaremill.sttp.client3" %% "circe" % sttpVersion
    )
  )

val model = (project in file("model"))
  .settings(
    name := "model"
  )

val tools = (project in file("tools"))
  .settings(
    name := "model",
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.4.1"
    )
  )
  .dependsOn(client)
