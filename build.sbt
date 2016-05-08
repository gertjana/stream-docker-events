name := "stream-docker-events"

organization := "net.addictivesoftware"

version := "0.1-SNAPSHOT"

libraryDependencies := Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.4.4",
  "io.spray" %%  "spray-json" % "1.3.2",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0",
  "org.specs2" %% "specs2-core" % "3.7.2" % "test"
)

enablePlugins(AppPlugin)

enablePlugins(sbtdocker.DockerPlugin, JavaAppPackaging)

dockerfile in docker := {
  val appDir: File = stage.value
  val targetDir = "/app"

  new Dockerfile {
    from("java")
    entryPoint(s"$targetDir/bin/${executableScriptName.value}")
    copy(appDir, targetDir)
  }
}
