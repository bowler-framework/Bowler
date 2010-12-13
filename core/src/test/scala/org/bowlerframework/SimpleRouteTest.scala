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
        scope.params("name") + " : " + scope.params("company")
      }
    })
    
    get("/hello/wille/recursivity") {
      println(this.body)
      assert("wille : recursivity".equals(this.body))
    }
	}

  test("test splat"){
    BowlerConfigurator.get("/say/*/to/*", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        val list = scope.params("splat").asInstanceOf[List[String]]
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
        val list = scope.params("captures").asInstanceOf[List[String]]
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
        println(scope.params)
        scope.params("name")
      }
    })


    this.get("/index", ("name", "wille")){
       assert(this.body.equals("wille"))
    }

  }

  test("headers"){
    BowlerConfigurator.get("/json", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        var accepts = scope.request.getHeader("accept")
        val content = ContentTypeResolver.contentType(accepts)
        ContentTypeResolver.contentString(content)
      }
    })

    BowlerConfigurator.get("/html", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        var accepts = scope.request.getHeader("accept")
        val content = ContentTypeResolver.contentType(accepts)
        ContentTypeResolver.contentString(content)
      }
    })


    BowlerConfigurator.get("/xml", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        var accepts = scope.request.getHeader("accept")
        val content = ContentTypeResolver.contentType(accepts)
        ContentTypeResolver.contentString(content)
      }
    })


    this.get("/html", Seq.empty, Map("accept" -> "application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*//*;q=0.5")){
      println("PRINTLN BODY: " +  this.body)
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
      assert(func(Integer.parseInt(requestScope.request.getParameter("addValue")).asInstanceOf[R]) == 8)
    }

  }

  def intAdd(v: Int) = v + 4




  /*test("implied type"){
    println(add(3)(intAdd(_)))

    println(add("Hello")(println(_)))

    println(add(3, 4)(multiAdd(_, _)))

    println(add(3, "Hello: ")(multiAdd(_,_)))

    println(add(List("hello", "world"), "Wille")((b,c) => multiAdd(b,c)))

    println(add(List("hello", "world"), "Wille")((b,c) => {
      println(b)
      println(c)
    }))
  }

  def intAdd(v: Int) = v + 4

  def multiAdd(v: Int, x: Int) = v + x

  def multiAdd(v: Int, x: String) = println("multiAdd: " + x + v)

  def multiAdd(v: List[String], x: String) = v.foreach(f => println(f + " " + x))



  def add[R](l: R)(func: R => Any)(implicit m: Manifest[R]): Any ={
    println(m.toString)
    //println(fields(0).getName)
    //println(field.)
    func(l)
  }

  def add[R,S](l: R, k: S)(func: (R,S) => Any)(implicit m: Manifest[R], t: Manifest[S]): Any ={
    println("implicit: " + m.toString + ", " + t.toString)
    m.typeArguments.foreach(p => println("generics: " + p))
    //m.
    //println(fields(0).getName)
    //println(field.)
    func(l,k)
  }  */


}
