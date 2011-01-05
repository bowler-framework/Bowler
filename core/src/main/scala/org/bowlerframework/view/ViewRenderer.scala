package org.bowlerframework.view

import org.bowlerframework.{Request, Response}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 28/12/2010
 * Time: 22:27
 * To change this layout use File | Settings | File Templates.
 */

trait ViewRenderer{
  def renderView(request: Request, response: Response, models: Any*)

  def renderView(request: Request, response: Response)

  def onError(request: Request, response: Response, exception: Exception)
}