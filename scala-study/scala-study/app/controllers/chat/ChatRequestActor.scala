package controllers.chat

import akka.actor.ActorRef
import akka.actor.Actor
import akka.actor.Props
import play.api.libs.json.JsValue
import domains.Chat
import domains.Talk
import domains.Join
import domains.Leave
import akka.actor.PoisonPill

class ChatRequestActor(out: ActorRef, userName: String) extends Actor {
  import ChatMessageConverters._
  
  override def receive: Receive = {
    case msg: JsValue => 
      println(msg.toString())
      val chat = msg.as[Chat]
      out ! Talk(chat.userName, chat.text)      
  }
  
  override def preStart() : Unit = out ! Join(userName)
  
  override def postStop(): Unit = {
    out ! Leave(userName)
    out ! PoisonPill
  }
}

object ChatRequestActor {
  def props(out: ActorRef, userName: String): Props = Props(new ChatRequestActor(out,userName)) 
}