package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._
import javax.inject.Inject
import javax.inject.Singleton
import play.api.i18n.MessagesApi

@Singleton
class UserController @Inject()(val messagesApi: MessagesApi) extends ControllerBase {
  /** Form定義 */
  val userform = Form(
    mapping(
      "name" -> nonEmptyText,
      "email" -> text)(UserData.apply)(UserData.unapply))

  /** 初期画面関数 */
  def entryInit = Action {
    val filledForm = userform.fill(UserData("user name", "email address"))
    Ok(views.html.user.entry(filledForm))
  }

  /** ユーザ登録関数 */
  def entrySubmit = Action { implicit request =>
     userform.bindFromRequest.fold(
      errors => {
        println("error!")
        BadRequest(views.html.user.entry(errors))
      },
      success => {
        println("entry success!")
        Ok(views.html.user.entrySubmit())
      })
  }
}