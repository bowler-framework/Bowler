package org.bowlerframework.view

import org.bowlerframework.{Response, Request}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 28/12/2010
 * Time: 22:40
 * To change this template use File | Settings | File Templates.
 */

class JsonViewRenderer extends ViewRenderer{
  def onError(request: Request, response: Response, exception: Exception) = null

  def render(request: Request, response: Response, models: Any*) = null
}