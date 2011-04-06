package org.bowlerframework.controller

import org.scalatra.test.scalatest.ScalatraFunSuite
import org.bowlerframework.http.BowlerFilter
import org.bowlerframework.{Response, Request}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 19/12/2010
 * Time: 21:25
 * To change this layout use File | Settings | File Templates.
 */

class InterceptingControllerTest extends ScalatraFunSuite {
  val holder = this.addFilter(classOf[BowlerFilter], "/*")
  holder.setInitParameter("controllerPackage", "org.bowlerframework.stub.controller")


  test("simple function passing") {
    val controller = new MyInterceptingController

    get("/interceptor") {
      assert(controller.success)
    }

  }

  test("double intercept") {
    val controller = new NestedInterceptingController
    get("/doubleIntercept") {
      assert(controller.success)
    }
  }

}

class NestedInterceptingController extends MyInterceptingController {
  override def around(request: Request, response: Response)(controller: (Request, Response) => Unit) = {
    super.around(request, response) {
      (x: Request, y: Response) => {
        push(2)
        controller(x, y)
        this.shouldBe = 3
      }
    }
  }

}


class MyInterceptingController extends InterceptingController {
  var shouldBe = 1
  var success = false

  def push(assertion: Int): Unit = {
    if (shouldBe == assertion) {}
    else throw new IllegalArgumentException("ShouldBe expected at " + shouldBe + " but was" + assertion)
    shouldBe = shouldBe + 1
  }

  def around(request: Request, response: Response)(controller: (Request, Response) => Unit) = {
    try {
      println("before")
      push(1)
      controller(request, response)
      push(3)
      success = true
      println("after")
    } catch {
      case e: IllegalArgumentException => {
        success = false
      }
    }


  }

  get("/doubleIntercept")((request, response) => {
    push(3)
  })

  get("/interceptor")((request, response) => {
    push(2)
  })


}