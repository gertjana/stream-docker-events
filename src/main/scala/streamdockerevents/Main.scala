package streamdockerevents

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}

case class Event(name:String, image:String, event:String)
object Event {
  private def extract(line:String, before:String, after:String):Option[String] = {
    if (line.contains(before) && line.contains(after)) {
      Some(line.split(before).tail.head.split(after).head)
    } else {
      None
    }
  }

  def apply(line:String): Option[Event] = {
    for {
      name <- extract(line, "name=", ",")
      image <- extract(line, "image=", ",")
      event <- extract(line, "container ", " ")
    } yield Event(name, image, event)
  }
}


object Main extends App {

  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()

  val source = Source.empty
  val sink = Sink.foreach(println)

  //val echoFlow = ShellCommandFlow("echo Hello World".split(" "))
  val dockerEventsFlow = ShellCommandFlow("docker events".split(" "))


  source
      .via(dockerEventsFlow)
      .map(l => Event(l))
      .to(sink)
      .run()

}
