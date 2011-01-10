package org.bowlerframework.view.scalate

import org.bowlerframework.view.ViewRenderer
import org.bowlerframework.{Response, Request}

/**
 * A ViewRenderer that uses Scalate templates to render views
 */

class ScalateViewRenderer extends ViewRenderer{
  def onError(request: Request, response: Response, exception: Exception) = null

  def renderView(request: Request, response: Response, models: Any*) = {
    val layout = TemplateRegistry.getLayout(request)
  }

  def renderView(request: Request, response: Response) = {
    val layout = TemplateRegistry.getLayout(request)
  }
}