package org.bowlerframework.view.scalate

import org.bowlerframework.view.ViewRenderer
import org.bowlerframework.{Response, Request}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 28/12/2010
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */

class MustacheViewRenderer extends ViewRenderer{
  def onError(request: Request, response: Response, exception: Exception) = null

  def renderView(request: Request, response: Response, models: Any*) = null

  def renderView(request: Request, response: Response) = null
}