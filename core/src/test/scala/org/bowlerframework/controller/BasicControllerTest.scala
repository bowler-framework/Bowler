package org.bowlerframework.controller


import org.scalatra.test.scalatest.ScalatraFunSuite
import org.bowlerframework.http.BowlerFilter
import util.matching.Regex
import org.bowlerframework.{Response, Request, BowlerConfigurator}

import org.bowlerframework.exception.ValidationException
import org.bowlerframework.view.{DefaultRenderStrategy, ViewRenderer, RenderStrategy}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 15/12/2010
 * Time: 20:19
 * To change this activeLayout use File | Settings | File Templates.
 */

class BasicControllerTest extends ScalatraFunSuite {
  val holder = this.addFilter(classOf[BowlerFilter], "/*")
  holder.setInitParameter("controllerPackage", "org.bowlerframework.stub.controller")

  test("Simple Controller Route") {
    val controller = new MyController
    get("/myController", ("name", "wille")) {
      assert(controller.responseString.equals("wille1"))
    }
  }

  test("json validation exception") {
    val controller = new MyController
    var status = 200
    val list = List[Tuple2[String, String]](("author.id", "" + (1)), ("author.firstName", "postAuthor"), ("author.lastName", "author"), ("author.email", "some@email.com"))
    this.post("/jsonValidationException", list, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")) {
      status = this.response.getStatus
    }

    println(status)
    assert(status == 400)
  }

  test("regex toString") {
    val regex: Regex = """^\/f(.*)/b(.*)""".r
    assert("""^\/f(.*)/b(.*)""".equals(regex.toString))
  }

  test("Successful validation block") {
    val controller = new MyController
    controller.responseString = "failure"
    post("/somePost", ("name", "wille")) {
      assert(controller.responseString.equals("success!"))
    }
  }



  test("Failed validation block") {
    val controller = new MyController
    BowlerConfigurator.setRenderStrategy(new RenderStrategy {
      def resolveViewRenderer(request: Request) = new ViewRenderer {
        def onError(request: Request, response: Response, exception: Exception) = {
          if (exception.isInstanceOf[ValidationException]) {
            exception.asInstanceOf[ValidationException].errors.foreach(e => {
              response.getWriter.write(e._1 + ":" + e._2)
              //response.sendError(400)
            })
          }
        }

        def renderView(request: Request, response: Response, models: Seq[Any]) = null

        def renderView(request: Request, response: Response) = null
      }
    })
    controller.responseString = "failure"
    post("/somePost") {
      println(controller.responseString)
      assert(!controller.responseString.equals("success!"))
      assert(this.response.getContent.equals("name:Name is a required parameter!"))
    }

    BowlerConfigurator.setRenderStrategy(new DefaultRenderStrategy)
  }

}
