package org.bowlerframework.view

import org.bowlerframework.Request
import scalate.ScalateViewRenderer

/**
 * A Default RenderStrategy - returns a JsonViewRenderer if the client has set the "accept" header to "application/json",<br/>
 * Otherwise returns a ScalateViewRenderer for rendering Scalate Web views
 */
class DefaultRenderStrategy extends RenderStrategy {
  def resolveViewRenderer(request: Request): ViewRenderer = {
    matchAccept(request.getHeader("accept")) match {
      case JSON => return JsonViewRenderer()
      case _ => return new ScalateViewRenderer
    }
  }

  private def matchAccept(acceptHeader: Option[String]): Accept = {
    if (acceptHeader == None)
      return HTML
    val lowerCase = acceptHeader.get.toLowerCase
    if (lowerCase.contains("text/html") || lowerCase.contains("application/xhtml+xml"))
      return HTML
    else if (lowerCase.contains("application/json") || lowerCase.contains("text/json"))
      return JSON
    else if (lowerCase.contains("application/xml") || lowerCase.contains("text/xml"))
      return XML
    else
      return HTML
  }
}

