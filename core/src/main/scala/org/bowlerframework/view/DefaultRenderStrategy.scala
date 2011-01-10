package org.bowlerframework.view

import org.bowlerframework.{ContentTypeResolver, Response, Request}
import scalate.ScalateViewRenderer

/**
 * A Default RenderStrategy - returns a JsonViewRenderer if the client has set the "accept" header to "application/json",<br/>
 * Otherwise returns a ScalateViewRenderer for rendering Scalate Web views
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