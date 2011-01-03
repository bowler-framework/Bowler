package org.bowlerframework.controller


import org.scalatra.test.scalatest.ScalatraFunSuite
import org.bowlerframework.http.BowlerFilter
import util.matching.Regex
import org.bowlerframework.view.{ViewRenderer, RenderStrategy}
import org.bowlerframework.{Response, Request, BowlerConfigurator, RequestScope}
import org.bowlerframework.model.ValidationException

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
    BowlerConfigurator.setRenderStrategy(new RenderStrategy{
      def resolveViewRenderer(request: Request) = new ViewRenderer{
        def onError(request: Request, response: Response, exception: Exception) = {
          if(exception.isInstanceOf[ValidationException]){
            exception.asInstanceOf[ValidationException].errors.foreach(e =>{
              response.getWriter.write(e._1 + ":" + e._2)
            })
          }
        }
        def renderView(request: Request, response: Response, models: Any*) = null

        def renderView(request: Request, response: Response) = null
      }
    })
    controller.responseString = "failure"
    post("/somePost"){
      println(controller.responseString)
      assert(!controller.responseString.equals("success!"))
      assert(this.response.getContent.equals("name:Name is a required parameter!"))
    }
  }

}
