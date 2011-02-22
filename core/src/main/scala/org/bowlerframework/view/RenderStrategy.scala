package org.bowlerframework.view

import org.bowlerframework.{Request, Response}

/**
 * Strategy for resolving a ViewRenderer given a request
 */
trait RenderStrategy{
  def resolveViewRenderer(request: Request): ViewRenderer
}