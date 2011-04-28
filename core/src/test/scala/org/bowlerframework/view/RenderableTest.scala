package org.bowlerframework.view

import org.scalatest.FunSuite
import org.bowlerframework.jvm.{DummyResponse, DummyRequest}
import scalate.{DefaultLayout, Layout, TemplateRegistry}
import scuery.stub.{ComposedPageComponent, SimpleTransformingComponent}
import java.io.{StringReader, StringWriter}
import org.bowlerframework.{Request, GET, Response, MappedPath}
import scuery.ViewComponentRegistry

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 28/12/2010
 * Time: 20:52
 * To change this activeLayout use File | Settings | File Templates.
 */

class RenderableTest extends FunSuite with Renderable {



  test("empty seq JSON") {
    val request = makeJsonRequest("/simple")
    request.setMappedPath(new MappedPath("/simple", false))
    val resp = makeResponse
    this.render(request, resp, List[Any]())
    assert("[]" == resp.toString)
  }

  test("non-empty seq JSON") {
    val request = makeJsonRequest("/simple")
    request.setMappedPath(new MappedPath("/simple", false))
    val resp = makeResponse
    this.render(request, resp, List("hello"))
    assert("[\"hello\"]" == resp.toString)
  }

  test("renderWith (empty)") {
    TemplateRegistry.defaultLayout = {(request: Request) => Option(DefaultLayout("renderable"))}
    val request = makeRequest("/index")
    request.setMappedPath(new MappedPath("/index", false))
    val resp = makeResponse
    this.renderWith(ViewPath(GET, MappedPath("/simple")), request, resp, List[Any]())
    assert(resp.toString.contains("Where's the list? Hello"))
  }

  test("renderWith Squery and layout"){
    TemplateRegistry.defaultLayout = {(request: Request) => Option(DefaultLayout("renderable"))}
    val request = makeRequest("/index")
    request.setMappedPath(new MappedPath("/index", false))
    val resp = makeResponse
    renderWith(new ComposedPageComponent(new SimpleTransformingComponent), request, resp)
    assert(resp.toString.contains("Where's the list?"))
    assert(resp.toString.contains("Mells"))
  }

  test("renderWith model") {
    TemplateRegistry.defaultLayout = {(request: Request) => None}
    TemplateRegistry.defaultLayout = {(request: Request) => Option(DefaultLayout("renderable"))}
    val request = makeRequest("/index")
    request.setMappedPath(new MappedPath("/index", false))
    val resp = makeResponse
    this.renderWith(ViewPath(GET, MappedPath("/simple")), request, resp, ViewModel("name", "Wille"))
    assert(resp.toString.contains("Where's the list? Hello"))
  }


  test("renderWith Squery (empty)") {
    TemplateRegistry.defaultLayout = {(request) => None}
    val resp = makeResponse
    val request = makeRequest("/somePath")
    this.renderWith(new ComposedPageComponent(new SimpleTransformingComponent), request, resp)
    assertSquery(resp)
  }

  test("render Squery with registered"){
    val resp = makeResponse
    val request = makeRequest("/somePath")
    ViewComponentRegistry.register(request, new ComposedPageComponent(new SimpleTransformingComponent))

    this.render(request, resp)
    assertSquery(resp)
  }

  def assertSquery(resp: Response) = {
    val result = scala.xml.XML.load(new StringReader(resp.toString))
    assert("James" == ((result \ "body" \ "div" \ "table" \\ "tr")(0) \ "td")(0).text)
    assert("Mells" == ((result \ "body" \ "div" \ "table" \\ "tr")(0) \ "td")(1).text)

    assert("Hiram" == ((result \ "body" \ "div" \ "table" \\ "tr")(1) \ "td")(0).text)
    assert("Tampa" == ((result \ "body" \ "div" \ "table" \\ "tr")(1) \ "td")(1).text)
    assert("A Title" == (result \ "head" \ "title").text)
  }

  test("renderWith Squery (model") {
    TemplateRegistry.defaultLayout = {(request) => None}
    val resp = makeResponse
    val request = makeRequest("/somePath")

    this.renderWith(new ComposedPageComponent(new SimpleTransformingComponent), request, resp, ViewModel("name", "Wille"), ViewModel("name", "Wille"))

    val result = scala.xml.XML.load(new StringReader(resp.toString))
    assert("James" == ((result \ "body" \ "div" \ "table" \\ "tr")(0) \ "td")(0).text)
    assert("Mells" == ((result \ "body" \ "div" \ "table" \\ "tr")(0) \ "td")(1).text)

    assert("Hiram" == ((result \ "body" \ "div" \ "table" \\ "tr")(1) \ "td")(0).text)
    assert("Tampa" == ((result \ "body" \ "div" \ "table" \\ "tr")(1) \ "td")(1).text)
    assert("A Title" == (result \ "head" \ "title").text)

  }

  test("renderWith Squery (json)") {
    TemplateRegistry.defaultLayout = {(request) => None}
    val resp = makeResponse
    val request = makeJsonRequest("/somePath")

    this.renderWith(new ComposedPageComponent(new SimpleTransformingComponent), request, resp, ViewModel("name", "Wille"), ViewModel("name", "Wille"))

    assert("{\"name\":\"Wille\",\"name\":\"Wille\"}" == resp.toString)
  }

  test("renderWith Squery (json no model)") {
    val resp = makeResponse
    val request = makeJsonRequest("/somePath")

    this.renderWith(new ComposedPageComponent(new SimpleTransformingComponent), request, resp)
    assert(resp.getStatus == 204)
  }

  test("empty seq HTML") {
    TemplateRegistry.defaultLayout = {(request: Request) => None}
    TemplateRegistry.defaultLayout = {(request: Request) => Option(DefaultLayout("renderable"))}
    val request = makeRequest("/simple")
    request.setMappedPath(new MappedPath("/simple", false))
    val resp = makeResponse
    this.render(request, resp, List[Any]())
    assert(resp.toString.contains("Where's the list? Hello"))

  }

  test("render 204 JSON") {
    val request = makeJsonRequest("/simple")
    request.setMappedPath(new MappedPath("/simple", false))
    val resp = makeResponse
    this.render(request, resp)
    assert(resp.getStatus == 204)
  }

  test("render with no template") {
    TemplateRegistry.defaultLayout = {(request: Request) => None}

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