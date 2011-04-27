package org.bowlerframework.view.scalate

import org.scalatest.FunSuite
import org.bowlerframework.jvm.{DummyResponse, DummyRequest}
import org.bowlerframework.view.ViewModel
import org.bowlerframework.{GET, Request, MappedPath}

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

  def makeRequest(path: String) = new DummyRequest(GET, path, Map(), null)
}

class TestLayoutModel extends LayoutModel {

  def model(request: Request, viewModel: Map[String, Any], viewIdAndValue: (String, String)) = {
    Map("title" -> request.getMappedPath.path, viewIdAndValue._1 -> viewIdAndValue._2)
  }

}