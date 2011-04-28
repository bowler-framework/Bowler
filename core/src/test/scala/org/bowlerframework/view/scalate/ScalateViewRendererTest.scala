package org.bowlerframework.view.scalate

import org.scalatest.FunSuite
import org.bowlerframework.jvm.{DummyResponse, DummyRequest}
import org.bowlerframework.view.ViewModel
import org.bowlerframework.{GET, Request, MappedPath}
import org.bowlerframework.view.scuery.stub.{HeaderAndDivComponent, MySimpleComponent}
import java.io.StringReader

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/01/2011
 * Time: 23:15
 * To change this template use File | Settings | File Templates.
 */

class ScalateViewRendererTest extends FunSuite {
  val renderer = new ScalateViewRenderer
  //RenderEngine.reset

  def toSeq(models: Any*): Seq[Any] = models.toSeq


  test("render view with simple activeLayout") {
    TemplateRegistry.defaultLayout = {(request: Request) => Option(DefaultLayout("simple"))}
    val request = makeRequest("/simple/")
    request.setLocales(List("es", "se"))
    request.setMappedPath(new MappedPath("/simple", false))

    val response = new DummyResponse

    renderer.renderView(request, response, toSeq(ViewModel("name", "Wille")))
    println("response: " + response.toString)
    assert("<div>Hello Wille</div>" == response.toString)
  }

  test("render view with nested activeLayout") {
    TemplateRegistry.defaultLayout = {(request: Request) => Option(DefaultLayout("simple", Some(DefaultLayout("parent"))))}
    val request = makeRequest("/simple/")

    request.setLocales(List("es", "se"))
    request.setMappedPath(new MappedPath("/simple", false))

    val response = new DummyResponse

    renderer.renderView(request, response, toSeq(ViewModel("name", "Wille")))
    println("response: " + response.toString)
    assert("<html><head><title>Parent</title></head><body><div>Hello Wille</div></body></html>" == response.toString)
  }

  test("render view with nested layout, where parent renders other child layouts") {
    TemplateRegistry.defaultLayout = {(request: Request) => Option(DefaultLayout("simple", Some(DefaultLayout("parent2", "doLayout", None, Some(new TestLayoutModel)))))}
    val request = makeRequest("/simple/")
    request.setLocales(List("es", "se"))
    request.setMappedPath(new MappedPath("/simple", false))

    val response = new DummyResponse

    renderer.renderView(request, response, toSeq(ViewModel("name", "Wille")))
    println("response: " + response.toString)
    assert("<html><head><title>/simple</title></head><body><div>Hello Wille</div></body></html>" == response.toString)
  }

  test("render view with Squery Layout"){
    TemplateRegistry.defaultLayout = {(request: Request) => Option(new ScueryLayout({(model: Map[String, Any]) => new MySimpleComponent}, "body", None))}
    val request = makeRequest("/simple/")
    request.setLocales(List("es", "se"))
    request.setMappedPath(new MappedPath("/simple", false))

    val response = new DummyResponse

    renderer.renderView(request, response, toSeq(ViewModel("name", "Wille")))
    assert(simple.toString == response.toString)
  }

  test("render view with nested Squery Layout"){
    TemplateRegistry.defaultLayout = {(request: Request) => Option(new ScueryLayout({(model: Map[String, Any]) => new HeaderAndDivComponent}, "div", Some(new ScueryLayout({(model: Map[String, Any]) => new MySimpleComponent}, "body", None))))}
    val request = makeRequest("/simple/")
    request.setLocales(List("es", "se"))
    request.setMappedPath(new MappedPath("/simple", false))

    val response = new DummyResponse

    renderer.renderView(request, response, toSeq(ViewModel("name", "Wille")))
    println(response.toString)
    assert(headerAndDiv.toString == response.toString)
  }

  test("render with nested Squery Layout inside DefaultLayout"){
    TemplateRegistry.defaultLayout = {(request: Request) => Option(new ScueryLayout({(model: Map[String, Any]) => new HeaderAndDivComponent}, "div", Some(DefaultLayout("parent", None))))}

    val request = makeRequest("/simple/")
    request.setLocales(List("es", "se"))
    request.setMappedPath(new MappedPath("/simple", false))

    val response = new DummyResponse

    renderer.renderView(request, response, toSeq(ViewModel("name", "Wille")))
    println(response.toString)
    assert("<html><head><title>Parent</title></head><body><span><h1>Header</h1><div>Hello Wille</div></span></body></html>" == response.toString)
  }

  def makeRequest(path: String) = new DummyRequest(GET, path, Map(), null)

  val simple = <html>
<head>
    <title>A Title</title>
</head>
<body>Hello Wille</body>
</html>

  val headerAndDiv = <html>
<head>
    <title>A Title</title>
</head>
<body><span><h1>Header</h1><div>Hello Wille</div></span></body>
</html>
}

class TestLayoutModel extends LayoutModel {

  def model(request: Request, viewModel: Map[String, Any], viewIdAndValue: (String, String)) = {
    Map("title" -> request.getMappedPath.path, viewIdAndValue._1 -> viewIdAndValue._2)
  }

}