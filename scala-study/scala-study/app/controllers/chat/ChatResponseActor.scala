package controllers.chat

import akka.actor.ActorRef
import akka.actor.Actor
import akka.actor.Props
import domains.Talk
import domains.Chat
import domains.Join
import play.api.libs.json.Json
import domains.Leave
import akka.actor.PoisonPill

class ChatResponseActor(out: ActorRef, me:String) extends Actor {
  import ChatMessageConverters._
  
  override def receive: Receive = {
    case Talk(u, t) =>
      out ! Json.toJson(Chat(u,t,false))
    case Join(u) =>
      out ! Json.toJson(Chat(u,"joined.",true))
    case Leave(userName) =>
      out ! Json.toJson(Chat(userName,"left.", true))
      if(userName == me) {
        out ! PoisonPill
        self ! PoisonPill
      }
  }
  
  override def postStop(): Unit = super.postStop()
}

object ChatResponseActor {
  def props(out: ActorRef, me: String) : Props = Props(new ChatResponseActor(out,me))
}