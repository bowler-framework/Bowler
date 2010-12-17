package org.bowlerframework

import http.BowlerFilter
import org.scalatra.test.scalatest.ScalatraFunSuite
import scala.reflect.Manifest
import java.lang.reflect.{Field, Method}


class SimpleRouteTest extends ScalatraFunSuite{

  val holder = this.addFilter(classOf[BowlerFilter], "/*")
  holder.setInitParameter("controllerPackage", "org.bowlerframework.stub.controller")

	test("simple named param"){
    var body: String = null
    BowlerConfigurator.get("/hello/:name/:company", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        body = scope.request.getParameter("name") + " : " + scope.request.getParameter("company")
      }
    })
    
    get("/hello/wille/recursivity") {
      assert("wille : recursivity".equals(body))
    }
	}

  test("test splat"){
    var body: String = null
    BowlerConfigurator.get("/say/*/to/*", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        val list = scope.request.getParameterValues("splat")
        body = list(0) + " " + list(1)
      }
    })

    get("/say/hello/to/wille"){
      assert("hello wille".equals(body))
    }
  }

  test("test regex"){
    val regex = """^\/f(.*)/b(.*)""".r
    var body: String = null

    BowlerConfigurator.get(regex, new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        val list = scope.request.getParameterValues("captures")
        body = list(0) + " " + list(1)
      }
    })

    get("/foo/bar"){
      assert("oo ar".equals(body))
    }
  }

  test("simple get"){
    var body: String = null
    BowlerConfigurator.get("/index", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        body = scope.request.getStringParameter("name")
      }
    })


    this.get("/index", ("name", "wille")){
       assert(body.equals("wille"))
    }

  }

  test("contentType headers"){
    var body: String = null
    BowlerConfigurator.get("/json", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        val content = scope.request.getAccepts
        body = ContentTypeResolver.contentString(content)
      }
    })

    BowlerConfigurator.get("/html", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        val content = scope.request.getAccepts
        body = ContentTypeResolver.contentString(content)
      }
    })


    BowlerConfigurator.get("/xml", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        val content = scope.request.getAccepts
        body = ContentTypeResolver.contentString(content)
      }
    })


    this.get("/html", Seq.empty, Map("accept" -> "application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      assert(body.equals("text/html"))
    }
    this.get("/xml", Seq.empty, Map("accept" -> "application/xml,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      assert(body.equals("application/xml"))
    }
    this.get("/json", Seq.empty, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      assert(body.equals("application/json"))
    }

  }

  test("routeExecutor"){
    BowlerConfigurator.get("/executor", new RouteExecutorImpl[Int](b => intAdd(b)))
    this.get("/executor", ("addValue", "4")){}
  }

  class RouteExecutorImpl[R](func: R => Any)(implicit m: Manifest[R]) extends RouteExecutor{

    def executeRoute(requestScope: RequestScope): Unit = {
      assert(m.toString.equals("Int"))
      assert(func(Integer.parseInt(requestScope.request.getStringParameter("addValue")).asInstanceOf[R]) == 8)
    }

  }

  def intAdd(v: Int) = v + 4

}
