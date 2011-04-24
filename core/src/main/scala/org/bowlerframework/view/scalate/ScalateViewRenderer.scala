package org.bowlerframework.view.scalate

import java.io.{StringWriter, PrintWriter}
import org.bowlerframework.{GET, HTTP, Response, Request}
import org.bowlerframework.view.squery.ViewComponentRegistry

/**
 * A ViewRenderer that uses Scalate templates to render views
 */
class ScalateViewRenderer extends BrowserViewRenderer {

  protected def render(request: Request, response: Response, model: Map[String, Any]) = {
    val viewValue: String = {
      val squeryView = ViewComponentRegistry(request, model)
      squeryView match{
        case None => {
          val view = TemplateRegistry.templateResolver.resolveViewTemplate(request)
          val engine = RenderEngine.getEngine
          val writer = new StringWriter
          val pw = new PrintWriter(writer)
          val context = new BowlerRenderContext(view.uri, engine, pw)
          context.render(view.uri, model)
          writer.toString
        }
        case Some(component) => component.render.toString
      }
    }

    Layout.activeLayout(request) match{
      case None => response.getWriter.write(viewValue)
      case Some(layout) => renderLayout(layout, request, response, model, viewValue)
    }

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

    layout.parentLayout match{
      case None => writer = response.getWriter
      case Some(parent) => writer = new PrintWriter(stringWriter)
    }

    val responseContext = new BowlerRenderContext(TemplateRegistry.templateResolver.resolveLayout(request, layout).uri, engine, writer)
    responseContext.render(parent.uri, layoutModel.toMap)
    if (layout.parentLayout != None)
      renderLayout(layout.parentLayout.get, request, response, viewModel, stringWriter.toString)
  }

}