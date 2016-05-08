package streamdockerevents

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}

import scala.concurrent.Await

import scala.concurrent.duration._

object Main extends App with EventProtocol {
  val command = "docker events".split(" ")

  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()

  sys addShutdownHook {
    Await.result(system.terminate(), 5.seconds)
  }

  val result = Map[String, Event]()

  val source = Source.empty
  val sink = Sink.foreach(println)

  val dockerEventsFlow = ShellCommandFlow(command)

  source
    .via(dockerEventsFlow).map(l => Event(l)).filter(_.isDefined).map(_.get)
    .groupBy(20, _.name)
    .fold(("", List.empty[String])) {
      case ((_, list), event) =>
        println(s"${(event.name, event.event :: list)}"); (event.name, event.event :: list)
    }
    .map(x => x) // send to ui?
    .mergeSubstreams
    .to(sink)
    .run()
}