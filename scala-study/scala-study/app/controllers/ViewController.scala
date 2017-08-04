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

@Singleton
class ViewController @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action{
    val customer = new Customer("テストお客","テスト住所",23)
    
    val orders = List(
     new Order("a",200)
      ,new Order("b",300)
      ,new Order("c",400)
      ,new Order("d",500)
    )
    
    
    Ok(views.html.viewtest(customer, orders))
  }
  
  def template = Action{
    Ok(views.html.content())
  }
}
