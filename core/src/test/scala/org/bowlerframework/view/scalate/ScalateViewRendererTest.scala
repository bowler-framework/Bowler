package org.bowlerframework.view.scalate

import org.scalatest.FunSuite
import selectors.DefaultLayoutSelector
import org.bowlerframework.jvm.{DummyResponse, DummyRequest}
import org.bowlerframework.view.ViewModel
import org.bowlerframework.{Request, MappedPath, HTTP}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 12/01/2011
 * Time: 23:15
 * To change this template use File | Settings | File Templates.
 */

class ScalateViewRendererTest extends FunSuite{
  val renderer = new ScalateViewRenderer
  //RenderEngine.reset

  def toSeq(models: Any*): Seq[Any] = models.toSeq


  test("render view with simple layout"){
    TemplateRegistry.reset
    TemplateRegistry.appendTemplateSelectors(List(new DefaultLayoutSelector(Layout("simple"))))
    val request = makeRequest("/simple/")
    request.setLocales(List("es", "se"))
    request.setMappedPath(new MappedPath("/simple", false))

    val response = new DummyResponse

    renderer.renderView(request, response, toSeq(ViewModel("name", "Wille")))
    println("response: " + response.toString)
    assert("<div>Hello Wille</div>" == response.toString)
  }

  test("render view with nested layout"){
    TemplateRegistry.reset
    TemplateRegistry.appendTemplateSelectors(List(new DefaultLayoutSelector(Layout("simple", Some(Layout("parent"))))))
    val request = makeRequest("/simple/")
    request.setLocales(List("es", "se"))
    request.setMappedPath(new MappedPath("/simple", false))

    val response = new DummyResponse

    renderer.renderView(request, response, toSeq(ViewModel("name", "Wille")))
    println("response: " + response.toString)
    assert("<html><head><title>Parent</title></head><body><div>Hello Wille</div></body></html>" == response.toString)
  }

  test("render view with nested layout, where parent renders other child layouts"){
    TemplateRegistry.reset
    TemplateRegistry.appendTemplateSelectors(List(new DefaultLayoutSelector(Layout("simple", Some(Layout("parent2", None, new TestLayoutModel))))))
    val request = makeRequest("/simple/")
    request.setLocales(List("es", "se"))
    request.setMappedPath(new MappedPath("/simple", false))

    val response = new DummyResponse

    renderer.renderView(request, response, toSeq(ViewModel("name", "Wille")))
    println("response: " + response.toString)
    assert("<html><head><title>/simple</title></head><body><div>Hello Wille</div></body></html>" == response.toString)
  }

  def makeRequest(path: String) = new DummyRequest(HTTP.GET, path, Map(), null)
}

class TestLayoutModel extends LayoutModel{
  def model(request: Request, viewModel: Map[String, Any], childView: String) = {

    Map("title" -> request.getMappedPath.path, "doLayout" -> childView)
  }
}