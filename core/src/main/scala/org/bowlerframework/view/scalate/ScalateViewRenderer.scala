package org.bowlerframework.view.scalate

import org.bowlerframework.view.ViewRenderer
import org.bowlerframework.{Response, Request}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 28/12/2010
 * Time: 23:13
 * To change this layout use File | Settings | File Templates.
 */

class ScalateViewRenderer extends ViewRenderer{
  def onError(request: Request, response: Response, exception: Exception) = null

  def renderView(request: Request, response: Response, models: Any*) = {
    TemplateRegistry.getLayout(request)
  }

  def renderView(request: Request, response: Response) = {
    val layout = TemplateRegistry.getLayout(request)
  }
}