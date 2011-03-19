package org.bowlerframework.view

import org.bowlerframework.{Request}
import scalate.ScalateViewRenderer

/**
 * A Default RenderStrategy - returns a JsonViewRenderer if the client has set the "accept" header to "application/json",<br/>
 * Otherwise returns a ScalateViewRenderer for rendering Scalate Web views
 */
class DefaultRenderStrategy extends RenderStrategy{
  def resolveViewRenderer(request: Request): ViewRenderer = {
    Accept.matchAccept(request.getHeader("accept")) match{
      case JSON => return new JsonViewRenderer
      case _ => return new ScalateViewRenderer
    }
  }
}

