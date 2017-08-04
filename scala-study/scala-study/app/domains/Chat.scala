package domains

import akka.stream.scaladsl.Flow
import akka.stream.UniqueKillSwitch
  case class Chat(userName: String, text: String, systemFlag: Boolean)
