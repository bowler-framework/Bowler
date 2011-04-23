package org.bowlerframework.view.scalate

import org.bowlerframework.view.ViewRenderer
import java.io.{StringWriter, PrintWriter}
import org.bowlerframework.exception.{ValidationException, HttpException}
import collection.mutable.MutableList
import org.bowlerframework.model.ViewModelBuilder
import org.bowlerframework.{GET, HTTP, Response, Request}

/**
 * A ViewRenderer that uses Scalate templates to render views
 */
class ScalateViewRenderer extends BrowserViewRenderer {

  protected def render(request: Request, response: Response, model: Map[String, Any]) = {
    val view = TemplateRegistry.templateResolver.resolveViewTemplate(request)
    val engine = RenderEngine.getEngine
    val writer = new StringWriter
    val pw = new PrintWriter(writer)
    val context = new BowlerRenderContext(view.uri, engine, pw)
    context.render(view.uri, model)
    val viewValue = writer.toString
    val layout = Layout.activeLayout(request)
    if (layout != None)
      renderLayout(layout.get, request, response, model, viewValue)
    else
      response.getWriter.write(viewValue)

    if (request.getMethod == GET) {
      request.getSession.setLastGetPath(HTTP.relativeUrl(request.getPath))
    }
  }

  private def renderLayout(layout: Layout, request: Request, response: Response, viewModel: Map[String, Any], view: String) {
    val engine = RenderEngine.getEngine
    var layoutModel = layout.layoutModel.model(request, viewModel, view)

    val parent = TemplateRegistry.templateResolver.resolveLayout(request, layout)
    val stringWriter = new StringWriter
    var writer: PrintWriter = null
    if (layout.parentLayout == None)
      writer = response.getWriter
    else {
      writer = new PrintWriter(stringWriter)
    }

    val responseContext = new BowlerRenderContext(TemplateRegistry.templateResolver.resolveLayout(request, layout).uri, engine, writer)
    responseContext.render(parent.uri, layoutModel.toMap)
    if (layout.parentLayout != None)
      renderLayout(layout.parentLayout.get, request, response, viewModel, stringWriter.toString)
  }

}