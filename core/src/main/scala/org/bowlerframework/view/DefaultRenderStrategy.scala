package org.bowlerframework.view

import org.bowlerframework.{ContentTypeResolver, Response, Request}
import scalate.ScalateViewRenderer

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 28/12/2010
 * Time: 22:26
 * To change this layout use File | Settings | File Templates.
 */

class DefaultRenderStrategy extends RenderStrategy{
  def resolveViewRenderer(request: Request): ViewRenderer = {
    val contentType = ContentTypeResolver.contentType(request.getHeader("accept"))
    if(contentType.equals(ContentTypeResolver.JSON))
      return new JsonViewRenderer
    else
      return new ScalateViewRenderer
  }
}