package controllers

import play.api.mvc.Controller
import javax.inject.Inject
import javax.inject.Singleton
import play.api.mvc.Action
import play.api.mvc.Result
import play.api.mvc.ResponseHeader
import play.api.libs.iteratee.Enumerator
import play.Routes
import play.api.mvc.Request
import sun.net.httpserver.Request
import play.mvc.Http.RequestBody
import play.mvc.BodyParser.AnyContent
import java.io.File
import helper.LoggingAction
import helper.extAction
import models.Item
import play.api.libs.json.Json
import scala.util.parsing.json.JSON
import play.api.libs.Jsonp
import models.Customer
import models.Order
import play.api.i18n.MessagesApi
import play.api.data.Form
import play.api.data.Forms._
import models.Task
import dao.TaskDao
import play.api.libs.concurrent.Execution.Implicits.defaultContext

@Singleton
class TodoController @Inject() (val messagesApi: MessagesApi)(taskDao: TaskDao) extends ControllerBase {

  val taskForm = Form(
    mapping(
      "id" -> optional(number),
      "label" -> nonEmptyText)(Task.apply)(Task.unapply))
  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action.async {
    taskDao.all().map {
      tasks => Ok(views.html.todoIndex(tasks.toList, taskForm))
    }
  }

  def show = Action.async {
    taskDao.all().map {
      tasks => Ok(views.html.todoIndex(tasks.toList, taskForm))
    }
  }

  def regist = Action.async { implicit request =>
    /*
        val task: Task = taskForm.bindFromRequest.get
        taskDao.insert(task).map(_ => Redirect(routes.TodoController.index))
    * 
    */

    taskForm.bindFromRequest.fold(
      errors =>{
        print(errors)
        taskDao.all().map {
          tasks => BadRequest(views.html.todoIndex(tasks.toList, errors))
        }},
      success => {
        val task: Task = taskForm.bindFromRequest().get
        print(task.id)
        print(task.label)
        taskDao.insert(task).map(_ => Redirect(routes.TodoController.index))
      })
  }

  def update(id: Option[Int]) = Action {
    Ok("update")
  }
  def delete(id: Option[Int]) = Action {
    Ok("delete")
  }

  def deleteTask(id: Int) = Action {
    Ok
  }
  def newTask = Action {
    Ok
  }
}
