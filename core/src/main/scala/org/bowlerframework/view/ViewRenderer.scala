package org.bowlerframework.view

import org.bowlerframework.{Request, Response}

/**
 * Renders a View and any related Layouts if applicable.
 */

trait ViewRenderer{
  /**
   * Renders a view and associated layout(s) given one or more view model objects.
   */
  def renderView(request: Request, response: Response, models: Any*)
   /**
   * Renders a view and associated layout(s) with no model objects.
   */
  def renderView(request: Request, response: Response)
   /**
   * Renders an error view when an Exception occurs, for instance, this may render a view with
    * input errors displayed if a org.bowlerframework.exception.ValidationException is thrown, or a login prompt
    * if a org.bowlerframework.exception.ValidationException AccessDeniedException is thrown.
   */
  def onError(request: Request, response: Response, exception: Exception)
}