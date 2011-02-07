package org.bowlerframework.view

import org.scalatest.FunSuite
import java.io.StringWriter
import org.bowlerframework.jvm.{DummyResponse, DummyRequest}
import scalate.selectors.DefaultLayoutSelector
import scalate.{Layout, TemplateRegistry}
import org.bowlerframework.{GET, Response, MappedPath, HTTP}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 28/12/2010
 * Time: 20:52
 * To change this layout use File | Settings | File Templates.
 */

class RenderableTest extends FunSuite with Renderable{


  test("empty seq JSON"){
    val request = makeJsonRequest("/simple")
    request.setMappedPath(new MappedPath("/simple", false))
    val resp = makeResponse
    this.render(request, resp, List[Any]())
    assert("[]" == resp.toString)
  }

  test("non-empty seq JSON"){
    val request = makeJsonRequest("/simple")
    request.setMappedPath(new MappedPath("/simple", false))
    val resp = makeResponse
    this.render(request, resp, List("hello"))
    assert("[\"hello\"]" == resp.toString)
  }

  test("empty seq HTML"){
    TemplateRegistry.reset
    TemplateRegistry.appendLayoutSelectors(List(new DefaultLayoutSelector(Layout("renderable"))))
    val request = makeRequest("/simple")
    request.setMappedPath(new MappedPath("/simple", false))
    val resp = makeResponse
    this.render(request, resp, List[Any]())
    assert(resp.toString.contains("Where's the list? Hello"))

  }

  test("render 204 JSON"){
    val request = makeJsonRequest("/simple")
    request.setMappedPath(new MappedPath("/simple", false))
    val resp = makeResponse
    this.render(request, resp)
    assert(resp.getStatus == 204)
  }

  test("render with no template"){
    TemplateRegistry.reset

    val request = makeRequest("/simple")
    request.setMappedPath(new MappedPath("/simple", false))
    val resp = makeResponse
    this.render(request, resp, ViewModel("name", "Wille"))
    assert(resp.toString.contains("Hello Wille"))
  }


  def makeResponse: Response = {
    val writer = new StringWriter
    val resp = new DummyResponse(writer)
    return resp
  }

  def makeRequest(path: String) = new DummyRequest(GET, path, Map(), null)

  def makeJsonRequest(path: String) = new DummyRequest(GET, path, Map(), null, Map("accept" -> "application/json"))
}