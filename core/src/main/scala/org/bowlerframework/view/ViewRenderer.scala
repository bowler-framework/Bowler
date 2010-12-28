package org.bowlerframework.view

import org.bowlerframework.{Request, Response}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 28/12/2010
 * Time: 22:27
 * To change this template use File | Settings | File Templates.
 */

trait ViewRenderer{
  def render(request: Request, response: Response, models: Any*)

  def onError(request: Request, response: Response, exception: Exception)
}