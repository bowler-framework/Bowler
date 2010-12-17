package org.bowlerframework.controller


import org.scalatra.test.scalatest.ScalatraFunSuite
import org.bowlerframework.RequestScope
import org.bowlerframework.http.BowlerFilter
import util.matching.Regex

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
      assert(controller.responseString.equals("wille1"))
    }
  }

  test("regex toString"){
    val regex: Regex = """^\/f(.*)/b(.*)""".r
    assert("""^\/f(.*)/b(.*)""".equals(regex.toString))
  }

  test("Successful validation block"){
    val controller = new MyController
    controller.responseString = "failure"
    post("/somePost", ("name", "wille")){
      assert(controller.responseString.equals("success!"))
    }
  }



  test("Failed validation block"){
    val controller = new MyController
    controller.responseString = "failure"
    post("/somePost"){
      println(controller.responseString)
      assert(!controller.responseString.equals("success!"))
      assert(controller.responseString.equals("name:Name is a required parameter!"))
    }
  }

}
