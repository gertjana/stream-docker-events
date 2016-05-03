name := "stream-docker-events"

organization := "net.addictivesoftware"

version := "0.1-SNAPSHOT"

libraryDependencies := Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.4.4",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0",
  "org.specs2" %% "specs2-core" % "3.7.2" % "test"
)

enablePlugins(AppPlugin)
