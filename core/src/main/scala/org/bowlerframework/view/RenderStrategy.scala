package org.bowlerframework.view

import org.bowlerframework.{Request, Response}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 28/12/2010
 * Time: 22:24
 * To change this layout use File | Settings | File Templates.
 */

trait RenderStrategy{
  def resolveViewRenderer(request: Request): ViewRenderer

}