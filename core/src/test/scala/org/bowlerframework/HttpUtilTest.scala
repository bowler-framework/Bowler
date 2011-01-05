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


  test("HTTP basePath with servlet"){
    this.addServlet(new BowlerServlet, "/servlet/*")
    val c = new PathController

    get("/servlet/SimplePathController/1") {
      println(c.path)
      assert(c.path.equals("/servlet/"))
      assert(c.requestPath.equals("/SimplePathController/1"))
      assert(c.fullPath.equals("/servlet/SimplePathController/1"))
    }
  }

  test("HTTP basePath with filter"){
    val holder = this.addFilter(classOf[BowlerFilter], "/filter/*")
    holder.setInitParameter("controllerPackage", "org.bowlerframework.stub.controller")
    val c = new PathController
    get("/filter/filterPath/1") {
      assert(c.path.equals("/"))
      assert(c.requestPath.equals("/filter/filterPath/1"))
      assert(c.fullPath.equals("/filter/filterPath/1"))
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

  this.get("/filter/filterPath/:id")((req, resp) =>{
    path = HTTP.basePath
    fullPath = HTTP.relativeUrl(req.getPath)
    requestPath = req.getPath
  })
}