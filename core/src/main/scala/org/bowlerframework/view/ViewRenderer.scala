package org.bowlerframework.view

import org.bowlerframework.{Request, Response}
import org.bowlerframework.model.AliasRegistry

/**
 * Renders a View and any related Layouts if applicable.
 */
trait ViewRenderer {
  /**
   * Renders a view and associated layout(s) given one or more view model objects.
   */
  def renderView(request: Request, response: Response, models: Seq[Any])

  /**
   * Renders an error view when an Exception occurs, for instance, this may render a view with
   * input errors displayed if a org.bowlerframework.exception.ValidationException is thrown, or a login prompt
   * if a org.bowlerframework.exception.ValidationException AccessDeniedException is thrown.
   */
  def onError(request: Request, response: Response, exception: Exception)

  def getModelAlias(model: Any): String = {
    if (model.isInstanceOf[ViewModel])
      return model.asInstanceOf[ViewModel].alias
    else if (model.isInstanceOf[Tuple2[String, _]])
      return model.asInstanceOf[Tuple2[String, _]]._1
    else
      return AliasRegistry(model).get
  }

  def getModelValue(model: Any): Any = {
    if (model.isInstanceOf[ViewModel])
      return model.asInstanceOf[ViewModel].value
    else if (model.isInstanceOf[Tuple2[String, _]])
      return model.asInstanceOf[Tuple2[String, Any]]._2
    else
      return model
  }
}