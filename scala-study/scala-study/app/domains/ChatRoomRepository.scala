package domains

import akka.stream.scaladsl.Flow
import akka.stream.UniqueKillSwitch

trait ChatRoomRepository {
  def chatRoom(roomId: String, userName: String): ChatRoom
}