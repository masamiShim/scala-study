package controllers.chat

import play.api.libs.json.Writes
import play.api.libs.json.Reads
import domains.Chat
import play.api.libs.functional.syntax._
import play.api.libs.json.JsPath

object ChatMessageConverters {
  implicit val ChatMessageReads: Reads[Chat] = (
      (JsPath \ "userName").read[String] and
      (JsPath \ "text").read[String] and
      Reads.pure[Boolean](false)
      )(Chat)
     
  implicit val ChatMessageWrite: Writes[Chat] = (
      (JsPath \ "userName").write[String] and
      (JsPath \ "text").write[String] and
      (JsPath \ "systemFlag").write[Boolean]
      )(unlift(Chat.unapply))
}