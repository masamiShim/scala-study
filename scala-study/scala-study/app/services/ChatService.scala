package services

import javax.inject.Inject
import domains.ChatRoomRepository
import domains.ChatRoom

class ChatService @Inject()(
  repository: ChatRoomRepository
){
  
  def start(roomId: String, userName: String) : ChatRoom = repository.chatRoom(roomId, userName)
}