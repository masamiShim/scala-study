package helper

import play.api.mvc._
import scala.concurrent.Future

object extAction extends ActionBuilder[Request] {
  def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    block(request)
  }

  def onlyHttps[A](action: Action[A]) = Action.async(action.parser) { request =>
    request.headers.get("X-Forwarded-Proto").collect {
      case "https" => action(request)
    } getOrElse {
      Future.successful(play.api.mvc.Results.Forbidden("Only HTTPS requests allowed"))
    }
  }
  override def composeAction[A](action: Action[A]) = extAction.onlyHttps(action)
}