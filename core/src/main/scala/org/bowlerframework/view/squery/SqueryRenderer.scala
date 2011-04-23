package org.bowlerframework.view.squery

import org.bowlerframework._

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 14/03/2011
 * Time: 00:10
 * To change this template use File | Settings | File Templates.
 */

object SqueryRenderer {
  def apply(component: MarkupContainer): Unit = apply(component, RequestScope.request, RequestScope.response)

  def apply(component: MarkupContainer, request: Request, response: Response): Unit = {
    response.setContentType("text/html")
    response.getWriter.write(component.render.toString)
    request.getSession.resetValidations
    if (request.getMethod == GET)
      request.getSession.setLastGetPath(HTTP.relativeUrl(request.getPath))
  }
}