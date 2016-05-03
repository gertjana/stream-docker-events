package streamdockerevents

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.util.ByteString
import org.specs2.mutable.Specification
import org.specs2._

class ShellCommandFlowSpec extends Specification {

  "the shell command flow " >> {
    "should stream the output of any command given to it" >> {
      ok
    }
  }
}
