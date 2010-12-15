package org.bowlerframework.controller


import org.scalatra.test.scalatest.ScalatraFunSuite
import org.bowlerframework.RequestScope
import org.bowlerframework.http.BowlerFilter

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 15/12/2010
 * Time: 20:19
 * To change this template use File | Settings | File Templates.
 */

class BasicControllerTest extends ScalatraFunSuite{
  val holder = this.addFilter(classOf[BowlerFilter], "/*")
  holder.setInitParameter("controllerPackage", "org.bowlerframework.stub.controller")

  test("Simple Controller Route"){
    val controller = new MyController
    get("/myController", ("name", "wille")){
      assert(controller.response.equals("wille1"))
    }
  }
}

class MyController extends Controller{
  var i = 0
  var response: String = null

  get("/myController"){
    i = i + 1
    println("called")
    response = RequestScope.request.getStringParameter("name") + i
    println(response)
  }
}