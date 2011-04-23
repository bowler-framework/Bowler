package org.bowlerframework.http

import java.net.InetAddress
import org.bowlerframework._

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 13/12/2010
 * Time: 22:29
 * To change this activeLayout use File | Settings | File Templates.
 */

class BowlerHttpRequestTest extends org.scalatra.test.scalatest.ScalatraFunSuite {

  val holder = this.addFilter(classOf[BowlerFilter], "/*")
  holder.setInitParameter("applicationClass", "org.bowlerframework.stub.SimpleApp")

  test("getPath") {
    var body: String = null
    BowlerConfigurator.get("/getPath", new RouteExecutor {
      def executeRoute(scope: RequestScope) = {
        body = scope.request.getPath
      }
    })

    get("/getPath") {
      println(this.body)
      assert("/getPath".equals(body))
    }

  }

  test("test path variable with punctuation") {
    var body: String = null
    BowlerConfigurator.get("/punctuation/:id", new RouteExecutor {
      def executeRoute(scope: RequestScope) = {
        body = scope.request.getStringParameter("id")
      }
    })

    get("/punctuation/hello.world") {
      assert("hello.world".equals(body))
    }

  }

  test("getSession") {
    var body: String = null
    BowlerConfigurator.get("/getSession", new RouteExecutor {
      def executeRoute(scope: RequestScope) = {
        body = scope.request.getSession.getId
      }
    })

    get("/getSession") {
      assert(body != null)
    }

  }

  test("isSecure") {
    var body: String = null
    BowlerConfigurator.get("/isSecure", new RouteExecutor {
      def executeRoute(scope: RequestScope) = {
        body = scope.request.isSecure + ""
      }
    })

    get("/isSecure") {
      assert(body.equals("false"))
    }

  }

  test("getServerName") {
    var body: String = null
    BowlerConfigurator.get("/getServerName", new RouteExecutor {
      def executeRoute(scope: RequestScope) = {
        body = scope.request.getServerName
      }
    })

    get("/getServerName") {
      assert(body.equals(InetAddress.getLocalHost.getHostAddress))
    }
  }

  test("getIntParameter") {
    var i: Int = 0
    BowlerConfigurator.get("/getIntParameter/:int", new RouteExecutor {
      def executeRoute(scope: RequestScope) = {
        i = scope.request.getIntParameter("int")
      }
    })

    get("/getIntParameter/50") {
      assert(i == 50)
    }

  }

  test("getLongParameter") {
    var i: Long = 0l
    BowlerConfigurator.get("/getLongParameter/:long", new RouteExecutor {
      def executeRoute(scope: RequestScope) = {
        i = scope.request.getIntParameter("long")
      }
    })

    get("/getLongParameter/50") {
      assert(i == 50l)
    }
  }

  test("getBooleanParameter") {
    var i: Boolean = false
    BowlerConfigurator.get("/getBooleanParameter/:long", new RouteExecutor {
      def executeRoute(scope: RequestScope) = {
        i = scope.request.getBooleanParameter("long")
      }
    })

    get("/getBooleanParameter/true") {
      assert(i)
    }
  }


  test("getParameterNames") {
    var i: Iterable[String] = null
    BowlerConfigurator.get("/getParameterNames/:name/:value", new RouteExecutor {
      def executeRoute(scope: RequestScope) = {
        i = scope.request.getParameterNames
      }
    })

    get("/getParameterNames/foo/bar", ("param", "baz")) {
      assert(i.exists(p => {
        p.equals("name")
      }))
      assert(i.exists(p => {
        p.equals("value")
      }))
      assert(i.exists(p => {
        p.equals("param")
      }))
    }

  }

  test("HTTP Method") {
    var method: HttpMethod = null
    BowlerConfigurator.get("/getParameterNames/:name/:value", new RouteExecutor {
      def executeRoute(scope: RequestScope) = {
        method = scope.request.getMethod
      }
    })

    get("/getParameterNames/foo/bar", ("param", "baz")) {

      assert(GET.equals(method))
    }

  }


  test("GET ContentType") {
    var contentType: Option[String] = null
    BowlerConfigurator.get("/getParameterNames/:name/:value", new RouteExecutor {
      def executeRoute(scope: RequestScope) = {
        contentType = scope.request.getContentType
      }
    })

    this.get("/getParameterNames/foo/bar", ("param", "baz")) {
      println("CONTENT " + contentType)
      assert(contentType.equals(None))
    }

  }

  test("POST ContentType") {
    var ctype: Option[String] = null
    BowlerConfigurator.post("/getParameterNames/:name/:value", new RouteExecutor {
      def executeRoute(scope: RequestScope) = {
        ctype = scope.request.getContentType
      }
    })

    post("/getParameterNames/foo/bar", ("param", "baz")) {
      println("CONTENT-TYPE: " + ctype)
      assert(ctype.get.contains("application/x-www-form-urlencoded"))
    }
  }


  test("getLocales") {
    var locales: List[String] = null
    BowlerConfigurator.get("/getLocales", new RouteExecutor {
      def executeRoute(scope: RequestScope) = {
        locales = scope.request.getLocales
      }
    })

    get("/getLocales", Seq.empty, Map("accept-language" -> "fi, en-US")) {
      assert(locales(0).equals("fi"))
      assert(locales(1).equals("en_US"))
    }
  }



  test("getRequestBodyAsString") {
    val json = "{\"body\": \"send us some JSON will ya!\"}"
    var requestBody: String = null
    var content: ContentTypeResolver.ContentType = null
    BowlerConfigurator.post("/getRequestBodyAsString", new RouteExecutor {
      def executeRoute(scope: RequestScope) = {
        requestBody = scope.request.getRequestBodyAsString
        content = ContentTypeResolver.contentType(scope.request.getAccept)
      }
    })
    val headers = Map("accept" -> "application/json")

    post("/getRequestBodyAsString", json, headers) {
      assert(content.equals(ContentTypeResolver.JSON))
      assert(json.equals(requestBody))
    }
  }


}