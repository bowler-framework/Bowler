package org.bowlerframework.view.scalate.selectors

import org.scalatest.FunSuite
import org.bowlerframework.jvm.DummyRequest
import util.matching.Regex
import org.bowlerframework.extractors._
import org.bowlerframework.{POST, GET}
import org.bowlerframework.view.scalate.{DefaultLayout, Layout}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 05/01/2011
 * Time: 22:04
 * To change this activeLayout use File | Settings | File Templates.
 */

class ExtractorsTest extends FunSuite {
  test("defaultLayoutSelector") {
    val selector = new Default[DefaultLayout](DefaultLayout("default"))

    makeRequest(Map()) match{
      case selector(layout) => assert(layout.name == "default")
    }
  }


  test("headerContainsLayoutSelector") {
    val selector = new HeadersContain[DefaultLayout](DefaultLayout("default"), Map("accept" -> "application/json"))
    makeRequest(Map()) match{
      case selector(layout) => fail
      case _ => {}//expected
    }

    val sel = new HeadersContain[DefaultLayout](DefaultLayout("default"), Map("accept" -> "text/html"))
    makeRequest(Map()) match{
      case sel(layout) => assert(layout.name == "default")
    }
  }

  test("HeaderLayoutSelector") {
    val selector = new HeadersMatch[DefaultLayout](DefaultLayout("default"), Map("accept" -> new Regex("^.*application/json.*$")))
    makeRequest(Map()) match{
      case selector(layout) => fail
      case _ => {}//expected
    }

    val sel = new HeadersMatch[DefaultLayout](DefaultLayout("default"), Map("accept" -> new Regex("^.*text/html.*$")))
    makeRequest(Map()) match{
      case sel(layout) => assert(layout.name == "default")
    }
  }

  test("uri matcher selector") {
    val selector = new UriMatches[DefaultLayout](DefaultLayout("default"), new Regex("^.*/hello/.*$"))
    makeRequest(Map()) match{
      case selector(layout) => fail
      case _ => {}//expected
    }

    new DummyRequest(POST, "/hello/world", Map(), null) match{
      case selector(layout) => assert(layout.name == "default")
    }
  }

  test("uri & method matcher selector") {
    val selector = new UriAndMethodMatches[DefaultLayout](DefaultLayout("default"), GET, new Regex("^.*/hello/.*$"))
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