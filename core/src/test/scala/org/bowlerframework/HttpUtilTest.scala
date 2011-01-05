package org.bowlerframework

import controller.Controller
import http.{BowlerServlet, BowlerFilter}
import org.scalatra.test.scalatest.ScalatraFunSuite

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 04/01/2011
 * Time: 23:39
 * To change this template use File | Settings | File Templates.
 */

class HttpUtilTest extends ScalatraFunSuite{

  //val holder = this.addServlet(classOf[BowlerServlet], "/filter/*")
 // holder.setInitParameter("controllerPackage", "org.bowlerframework.stub.controller")
  this.addServlet(new BowlerServlet, "/filter/*")

  test("HTTP basePath"){
    val c = new PathController

    get("/filter/SimplePathController/1") {
      println(c.path)
      assert(c.path.equals("/filter/"))
      assert(c.requestPath.equals("/SimplePathController/1"))
      assert(c.fullPath.equals("/filter/SimplePathController/1"))
    }
  }

}

class PathController extends Controller{
  var path: String = null
  var requestPath: String = null
  var fullPath: String = null

  this.get("/SimplePathController/:id")((req, resp) =>{
    path = HTTP.basePath
    fullPath = HTTP.relativeUrl(req.getPath)
    requestPath = req.getPath
  })
}