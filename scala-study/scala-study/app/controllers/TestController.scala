package controllers

import javax.inject.Inject
import play.api.mvc.Controller
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

@Singleton
class TestController @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index(name: String) = Action{
    Ok("Hello" + name + "!")
  }
  
  def index2 = LoggingAction {
 //   Ok(<h1>こんにちは、いやおはようございます</h1>).withSession("connected" -> "testPlaySession").as(HTML)
    Ok(<h1>こんにちは、いやおはようございます</h1>).as(HTML)
  }
  
  def delCookie = Action{
    Ok("Bye").withNewSession
  }
  
  def viewCookie = Action{request =>
    request.session.get("connected").map{ test =>
      Ok("Loaded " + test) 
    }.getOrElse{
      Unauthorized("Oops connected Un Loaded")
    }
  }
      
    def flashTest = Action{implicit request =>
      Redirect("/flash2").flashing(
          "success" -> "flash is work")
    }
   
    def haveFlash = Action{implicit request =>
      Ok{
        request.flash.get("success").getOrElse("Opps!!")
      }
  }
    
  def contentPraser = Action{request =>
      Ok("Got : " + request.body) 
  }
  
  def save = Action(parse.file(to = new File("C:/Users/shimbuichi/tmp/upload"))){ request =>
      Ok("Saved the request content to " + request.body)
  }

  def logAction = LoggingAction{
    Ok("Hello World!!")
  }
  
 def filterAction = extAction{
   Ok("success")
 }
 
 
 def list = Action {implicit request =>
   val items = new Item(1,"","").findAll()
   Ok
 }
  /*Action {
    val title: String = "aaaaaaa"
    //Ok("Hello World")
    //NotFound
    //NotFound(<h1> Page not found </h1>)
    //BadRequest(views.html.form(formWithErros))
    //InternalServerError("Oops")
    //Status(299)("肉球")
    //Redirect("/")
    //Redirect("/",MOVED_PERMANENTLY)
    }*/

}
