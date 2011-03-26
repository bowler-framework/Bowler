package org.bowlerframework.view.squery

import org.bowlerframework.{RequestScope, Response}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 14/03/2011
 * Time: 00:10
 * To change this template use File | Settings | File Templates.
 */

object SqueryRenderer{
  def render(component: Component): Unit = render(component, RequestScope.response)

  def render(component: Component, response: Response): Unit = response.getWriter.write(component.render.toString)
}