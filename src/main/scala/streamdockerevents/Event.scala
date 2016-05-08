package streamdockerevents

import spray.json.DefaultJsonProtocol


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
      name  <- extract(line, "name=", ",")
      image <- extract(line, "image=", ",")
      event <- extract(line, "container ", " ")
    } yield Event(name, image, event)
  }
}

trait EventProtocol extends DefaultJsonProtocol {
  implicit val EventJson = jsonFormat3(Event.apply)
}