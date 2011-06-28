package org.bowlerframework.view.scalate

import java.io.{StringWriter, PrintWriter}
import org.bowlerframework.{GET, HTTP, Response, Request}
import org.bowlerframework.view.scuery.ViewComponentRegistry

/**
 * A ViewRenderer that uses Scalate templates to render views
 */
class ScalateViewRenderer extends BrowserViewRenderer {

  protected def render(request: Request, response: Response, model: Map[String, Any]) = {
    if (request.getMethod == GET){
      try{
        request.getSession.setLastGetPath(HTTP.relativeUrl(request.getPath))
      }catch{
        case e: IllegalStateException => println("IllegalStateException commiting lastGetPath: " + request.getPath)
      }

    }
    val viewValue: String = {
      ViewComponentRegistry(request, model) match{
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
      case Some(layout) => layout.render(request, response, model, viewValue)
    }
  }
}