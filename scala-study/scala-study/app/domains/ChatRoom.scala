package domains

import akka.stream.UniqueKillSwitch
import akka.stream.scaladsl.Flow

 case class ChatRoom(roomId: String, bus: Flow[ChatMessage, ChatMessage, UniqueKillSwitch])