package helper

import play.api.mvc.Action
import play.Logger

object Logging {
  
  def logging[A](action: Action[A]) = Action.async(action.parser) { request =>
    Logger.info("Calling Action logging")
    action(request)
  }
}