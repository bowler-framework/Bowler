package org.bowlerframework.controller

import org.scalatest.FunSuite
import org.bowlerframework.view.scalate.Layout
import org.bowlerframework.http.BowlerFilter
import org.scalatra.test.scalatest.ScalatraFunSuite

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 23/04/2011
 * Time: 00:45
 * To change this template use File | Settings | File Templates.
 */

class LayoutAwareTest extends ScalatraFunSuite{
  val holder = this.addFilter(classOf[BowlerFilter], "/*")

  test("Controller scoped activeLayout"){
    val controller = new LayoutAwareController
    get("/layoutAware/", ("name", "wille")) {
      assert(controller.currentLayout.name == "ControllerScoped")
    }
  }

  test("route scoped activeLayout"){
    val controller = new LayoutAwareController
    get("/layoutAware/route/", ("name", "wille")) {
      assert(controller.currentLayout.name == "RouteScoped")
    }
  }

}

class LayoutAwareController extends Controller with LayoutAware{
  layout(Layout("ControllerScoped"))
  var currentLayout: Layout = null

  this.get("/layoutAware/")((request, response ) => {
    currentLayout = Layout.activeLayout(request).get

  })

  this.get("/layoutAware/route/")((request, response ) => {
    layout(Layout("RouteScoped"))


    currentLayout = Layout.activeLayout(request).get
  })
}