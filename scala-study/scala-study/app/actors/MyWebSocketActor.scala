package actors

import akka.actor.ActorRef
import akka.actor.Props
import akka.actor.Actor

object MyWebSocketActor {
  def props(out: ActorRef) = Props(new MyWebSocketActor(out))
}

class MyWebSocketActor(out: ActorRef) extends Actor {
 def receive = {
   case msg: String =>
     out ! ("I received your message : " + msg) 
     println("RECIEVED MESSAGE " + msg);
 }
}