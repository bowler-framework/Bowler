package org.bowlerframework.controller

import org.scalatest.FunSuite
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

class InterceptingControllerTest extends ScalatraFunSuite{
  val holder = this.addFilter(classOf[BowlerFilter], "/*")
  holder.setInitParameter("controllerPackage", "org.bowlerframework.stub.controller")


  test("simple function passing"){
    val controller = new MyInterceptingController

    get("/interceptor") {
      assert(controller.success)
    }

  }

}


class MyInterceptingController extends InterceptingController{
  var shouldBe = 1
  var success = false

  def push(assertion: Int): Unit ={
    if(shouldBe == assertion){}
    else throw new IllegalArgumentException("ShouldBe expected at " + shouldBe + " but was" + assertion)
    shouldBe = shouldBe + 1
  }

  def around(request: Request, response: Response)(controller: (Request, Response) => Unit) = {
    try{
      push(1)
      controller(request, response)
      push(3)
      success = true
    }catch{
      case e: IllegalArgumentException => {success = false}
    }


  }

  get("/interceptor")((request, response) =>{
    push(2)
  })


}