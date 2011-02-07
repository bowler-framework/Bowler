package org.bowlerframework.view.scalate.selectors

import org.scalatest.FunSuite
import org.bowlerframework.jvm.DummyRequest
import org.bowlerframework.view.scalate.Layout
import util.matching.Regex
import org.bowlerframework.{POST, GET, HTTP}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 05/01/2011
 * Time: 22:04
 * To change this layout use File | Settings | File Templates.
 */

class SelectorsTest extends FunSuite{
  test("defaultLayoutSelector"){
    val selector = new DefaultLayoutSelector(Layout("default"))
    assert(selector.find(makeRequest(Map())).get.name == "default")
  }

  test("headerContainsLayoutSelector"){
    var selector = new HeaderContainsLayoutSelector(Layout("default"), Map("accept" -> "application/json"))
    assert(selector.find(makeRequest(Map())) == None)

    selector = new HeaderContainsLayoutSelector(Layout("default"), Map("accept" -> "text/html"))
    assert(selector.find(makeRequest(Map())).get.name == "default")
  }

  test("HeaderLayoutSelector"){
    var selector = new HeaderLayoutSelector(Layout("default"), Map("accept" -> new Regex("^.*application/json.*$")))
    assert(selector.find(makeRequest(Map())) == None)

    selector = new HeaderLayoutSelector(Layout("default"), Map("accept" -> new Regex("^.*text/html.*$")))
    assert(selector.find(makeRequest(Map())).get.name == "default")

  }

  test("uri matcher selector"){
    val selector = new UriLayoutSelector(Layout("default"), new Regex("^.*/hello/.*$"))
    assert(selector.find(makeRequest(Map())) == None)

    assert(selector.find(new DummyRequest(POST, "/hello/world", Map(), null)).get.name == "default")
  }

  test("uri & method matcher selector"){
    val selector = new UriAndMethodLayoutSelector(Layout("default"), GET, new Regex("^.*/hello/.*$"))
    assert(selector.find(new DummyRequest(POST, "/hello/world", Map(), null)) == None)

    assert(selector.find(new DummyRequest(GET, "/hello/world", Map(), null)).get.name == "default")
  }

  def makeRequest(params: Map[String, Any]) = new DummyRequest(POST, "/", params, null)

}