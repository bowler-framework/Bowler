package org.bowlerframework.view

import org.scalatest.FunSuite
import java.io.StringWriter
import org.bowlerframework.jvm.{DummyResponse, DummyRequest}
import org.bowlerframework.{Response, MappedPath, HTTP}
import scalate.selectors.DefaultLayoutSelector
import scalate.{Layout, TemplateRegistry}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 28/12/2010
 * Time: 20:52
 * To change this layout use File | Settings | File Templates.
 */

class RenderableTest extends FunSuite with Renderable{
  TemplateRegistry.reset
  TemplateRegistry.appendLayoutSelectors(List(new DefaultLayoutSelector(Layout("renderable"))))

  test("empty seq JSON"){
    val request = makeJsonRequest("/simple")
    request.setMappedPath(new MappedPath("/simple", false))
    val resp = makeResponse
    this.render(request, resp, List[Any]())
    assert("[]" == resp.toString)
  }

  test("empty seq HTML"){
    val request = makeRequest("/simple")
    request.setMappedPath(new MappedPath("/simple", false))
    val resp = makeResponse
    this.render(request, resp, List[Any]())
    println(resp.toString)
    assert(resp.toString.contains("Where's the list? Hello"))

  }


  def makeResponse: Response = {
    val writer = new StringWriter
    val resp = new DummyResponse(writer)
    return resp
  }

  def makeRequest(path: String) = new DummyRequest(HTTP.GET, path, Map(), null)

  def makeJsonRequest(path: String) = new DummyRequest(HTTP.GET, path, Map(), null, Map("accept" -> "application/json"))
}