package infrastractures

import javax.inject.Inject
import akka.stream.Materializer
import akka.actor.ActorSystem
import domains.ChatRoomRepository
import domains._
import akka.stream.scaladsl.MergeHub
import akka.stream.scaladsl.BroadcastHub
import akka.stream.scaladsl.Keep
import akka.stream.scaladsl.Sink
import akka.stream.scaladsl.Flow
import akka.stream.UniqueKillSwitch
import akka.stream.KillSwitches
import scala.concurrent.duration._
import java.util.concurrent.atomic.AtomicReference

class ChatRoomClient @Inject()(
 implicit val materializer: Materializer,
 implicit val system: ActorSystem
)extends ChatRoomRepository{

  import ChatRoomClient._
  
  override def chatRoom(roomId: String, userName: String): ChatRoom = synchronized {
    roomPool.get.get(roomId) match {
      case Some(chatRoom) =>
        chatRoom
      case None =>
        val room = create(roomId)
        roomPool.get() += (roomId -> room)
        room
    }
  }
  
  private def create(roomId:String): ChatRoom = {
    //create bus parts.
    val (sink, source) = 
      MergeHub.source[ChatMessage](perProducerBufferSize = 16)
      .toMat(BroadcastHub.sink(bufferSize = 256))(Keep.both)
      .run()
      
      //connect "drain outlet"
      source.runWith(Sink.ignore)
      val channel = ChatChannel(sink, source)
      
      val bus: Flow[ChatMessage, ChatMessage, UniqueKillSwitch] = Flow.fromSinkAndSource(channel.sink, channel.source)
        .joinMat(KillSwitches.singleBidi[ChatMessage, ChatMessage])(Keep.right)
     .backpressureTimeout(3.seconds)
        .map { e =>
          println(s"$e $channel")
          e
        }
        ChatRoom(roomId, bus)
        
  }
}

object ChatRoomClient {
  private var rooms: scala.collection.mutable.Map[String, ChatRoom] = scala.collection.mutable.Map()
  
  val roomPool: AtomicReference[scala.collection.mutable.Map[String, ChatRoom]] =
    new AtomicReference[scala.collection.mutable.Map[String,ChatRoom]](rooms)
}