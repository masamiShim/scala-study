package infrastractures

import domains.ChatMessage
import akka.stream.scaladsl.Sink
import akka.NotUsed
import akka.stream.scaladsl.Source

case class ChatChannel (
  sink : Sink[ChatMessage, NotUsed],
  source : Source[ChatMessage, NotUsed]
)