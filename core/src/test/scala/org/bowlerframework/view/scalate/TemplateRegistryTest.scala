package org.bowlerframework.view.scalate

import org.scalatest.FunSuite
import selectors._
import util.matching.Regex
import org.bowlerframework.jvm.DummyRequest
import org.bowlerframework.{POST, GET, MappedPath, HTTP}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 05/01/2011
 * Time: 22:12
 * To change this layout use File | Settings | File Templates.
 */

class TemplateRegistryTest extends FunSuite{

  TemplateRegistry.reset

  TemplateRegistry.appendLayoutSelectors(List(new UriAndMethodLayoutSelector(Layout("uriAndMethod"), POST, new Regex("^.*/hello/.*$")),
    new UriLayoutSelector(Layout("uri"), new Regex("^.*/hello/.*$")), new DefaultLayoutSelector(Layout("default"))))

  TemplateRegistry.appendSuffixSelectors(List(new UriAndMethodSuffixSelector("uriAndMethod", POST, new Regex("^.*/hello/.*$")),
    new UriSuffixSelector("uri", new Regex("^.*/hello/.*$"))))

  test("get default layout"){
    assert("default" == TemplateRegistry.getLayout(new DummyRequest(GET, "/worldy/world", Map(), null)).get.name)
  }

  test("get URI specific layout"){
    assert("uri" == TemplateRegistry.getLayout(new DummyRequest(GET, "/hello/", Map(), null)).get.name)
  }

  test("get URI AND Method specific layout"){
    assert("uriAndMethod" == TemplateRegistry.getLayout(new DummyRequest(POST, "/hello/", Map(), null)).get.name)
  }

  test("get 2 suffixes"){
    assert(2 == TemplateRegistry.getSuffixes(new DummyRequest(POST, "/hello/", Map(), null)).size)
    assert(TemplateRegistry.getSuffixes(new DummyRequest(POST, "/hello/", Map(), null))(0) == "uriAndMethod")
    assert(TemplateRegistry.getSuffixes(new DummyRequest(POST, "/hello/", Map(), null))(1) == "uri")
  }

  test("get 1 suffixes"){
    assert(1 == TemplateRegistry.getSuffixes(new DummyRequest(GET, "/hello/", Map(), null)).size)
    assert(TemplateRegistry.getSuffixes(new DummyRequest(GET, "/hello/", Map(), null))(0) == "uri")
  }

  test("get no suffixes"){
    assert(0 == TemplateRegistry.getSuffixes(new DummyRequest(GET, "/worldy/world", Map(), null)).size)
  }

  test("regex string"){
    assert("^.*/hello/.*$" == new Regex("^.*/hello/.*$").toString)
  }

}