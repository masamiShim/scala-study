package controllers.chat

import akka.actor.ActorSystem
import akka.stream.Materializer
import javax.inject._
import play.api._
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import play.api.libs.json.JsValue
import play.api.i18n.MessagesApi
import akka.actor.ActorSystem
import akka.stream.Materializer
import play.api.mvc.WebSocket
import play.api.libs.streams.ActorFlow
import play.api.libs.json.JsValue
import akka.io.Tcp.Message
import controllers.ControllerBase
import services.ChatService
import domains.ChatMessage
import akka.stream.scaladsl.Flow
import akka.stream.scaladsl.Keep

@Singleton
class ChatController @Inject() (val messagesApi: MessagesApi)(
    implicit val system: ActorSystem,
    implicit val materializer: Materializer,
    streamChatService: ChatService) extends ControllerBase {

  def get = Action { implicit request =>
    Ok(views.html.chat())
  }

  def ws(roomId: String) = WebSocket.accept[JsValue, JsValue] { request =>
    val userName = request.queryString("user_name").headOption.getOrElse("anon")

    val userInput: Flow[JsValue, ChatMessage, _] = ActorFlow.actorRef[JsValue, ChatMessage](out => ChatRequestActor.props(out, userName))
    val room = streamChatService.start(roomId, userName)
    val userOutPut: Flow[ChatMessage, JsValue, _] = ActorFlow.actorRef[ChatMessage, JsValue](out => ChatResponseActor.props(out, userName))

    userInput.viaMat(room.bus)(Keep.right).viaMat(userOutPut)(Keep.right)
  }
}
