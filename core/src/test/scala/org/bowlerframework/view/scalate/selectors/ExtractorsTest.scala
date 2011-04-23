package org.bowlerframework.view.scalate.selectors

import org.scalatest.FunSuite
import org.bowlerframework.jvm.DummyRequest
import org.bowlerframework.view.scalate.Layout
import util.matching.Regex
import org.bowlerframework.extractors._
import org.bowlerframework.{POST, GET}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 05/01/2011
 * Time: 22:04
 * To change this activeLayout use File | Settings | File Templates.
 */

class ExtractorsTest extends FunSuite {
  test("defaultLayoutSelector") {
    val selector = new Default[Layout](Layout("default"))

    makeRequest(Map()) match{
      case selector(layout) => assert(layout.name == "default")
    }
  }


  test("headerContainsLayoutSelector") {
    val selector = new HeadersContain[Layout](Layout("default"), Map("accept" -> "application/json"))
    makeRequest(Map()) match{
      case selector(layout) => fail
      case _ => {}//expected
    }

    val sel = new HeadersContain[Layout](Layout("default"), Map("accept" -> "text/html"))
    makeRequest(Map()) match{
      case sel(layout) => assert(layout.name == "default")
    }
  }

  test("HeaderLayoutSelector") {
    val selector = new HeadersMatch[Layout](Layout("default"), Map("accept" -> new Regex("^.*application/json.*$")))
    makeRequest(Map()) match{
      case selector(layout) => fail
      case _ => {}//expected
    }

    val sel = new HeadersMatch[Layout](Layout("default"), Map("accept" -> new Regex("^.*text/html.*$")))
    makeRequest(Map()) match{
      case sel(layout) => assert(layout.name == "default")
    }
  }

  test("uri matcher selector") {
    val selector = new UriMatches[Layout](Layout("default"), new Regex("^.*/hello/.*$"))
    makeRequest(Map()) match{
      case selector(layout) => fail
      case _ => {}//expected
    }

    new DummyRequest(POST, "/hello/world", Map(), null) match{
      case selector(layout) => assert(layout.name == "default")
    }
  }

  test("uri & method matcher selector") {
    val selector = new UriAndMethodMatches[Layout](Layout("default"), GET, new Regex("^.*/hello/.*$"))
    makeRequest(Map()) match{
      case selector(layout) => fail
      case _ => {}//expected
    }

    new DummyRequest(GET, "/hello/world", Map(), null) match{
      case selector(layout) => assert(layout.name == "default")
    }
  }

  def makeRequest(params: Map[String, Any]) = new DummyRequest(POST, "/", params, null)

}