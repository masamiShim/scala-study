package helper

import scala.concurrent.Future
import play.Logger
import play.api.mvc._

object LoggingAction extends ActionBuilder[Request]{
  def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) ={
    block(request)
  }
  override def composeAction[A](action:Action[A]) = Logging.logging(action)
}

