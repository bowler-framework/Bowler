package org.bowlerframework

import http.BowlerFilter
import org.scalatra.test.scalatest.ScalatraFunSuite
import scala.reflect.Manifest
import java.lang.reflect.{Field, Method}


class SimpleRouteTest extends ScalatraFunSuite{

  val holder = this.addFilter(classOf[BowlerFilter], "/*")
  holder.setInitParameter("applicationClass", "org.bowlerframework.stub.SimpleApp")

	test("simple named param"){
    BowlerConfigurator.get("/hello/:name/:company", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        scope.request.getParameter("name") + " : " + scope.request.getParameter("company")
      }
    })
    
    get("/hello/wille/recursivity") {
      assert("wille : recursivity".equals(this.body))
    }
	}

  test("test splat"){
    BowlerConfigurator.get("/say/*/to/*", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        val list = scope.request.getParameterValues("splat")
        list(0) + " " + list(1)
      }
    })

    get("/say/hello/to/wille"){
      assert("hello wille".equals(this.body))
    }
  }

  test("test regex"){
    val regex = """^\/f(.*)/b(.*)""".r

    BowlerConfigurator.get(regex, new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        val list = scope.request.getParameterValues("captures")
        list(0) + " " + list(1)
      }
    })

    get("/foo/bar"){
      assert("oo ar".equals(this.body))
    }
  }

  test("simple get"){
    BowlerConfigurator.get("/index", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        scope.request.getParameter("name")
      }
    })


    this.get("/index", ("name", "wille")){
       assert(this.body.equals("wille"))
    }

  }

  test("contentType headers"){
    BowlerConfigurator.get("/json", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        val content = scope.request.getAcceptsContentType
        ContentTypeResolver.contentString(content)
      }
    })

    BowlerConfigurator.get("/html", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        val content = scope.request.getAcceptsContentType
        ContentTypeResolver.contentString(content)
      }
    })


    BowlerConfigurator.get("/xml", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        val content = scope.request.getAcceptsContentType
        ContentTypeResolver.contentString(content)
      }
    })


    this.get("/html", Seq.empty, Map("accept" -> "application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      assert(this.body.equals("text/html"))
    }
    this.get("/xml", Seq.empty, Map("accept" -> "application/xml,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      assert(this.body.equals("application/xml"))
    }
    this.get("/json", Seq.empty, Map("accept" -> "application/json,;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      assert(this.body.equals("application/json"))
    }

  }

  test("routeExecutor"){
    BowlerConfigurator.get("/executor", new RouteExecutorImpl[Int](b => intAdd(b)))
    this.get("/executor", ("addValue", "4")){}

  }

  class RouteExecutorImpl[R](func: R => Any)(implicit m: Manifest[R]) extends RouteExecutor{

    def executeRoute(requestScope: RequestScope): Any = {
      assert(m.toString.equals("Int"))
      assert(func(Integer.parseInt(requestScope.request.getStringParameter("addValue")).asInstanceOf[R]) == 8)
    }

  }

  def intAdd(v: Int) = v + 4

}
