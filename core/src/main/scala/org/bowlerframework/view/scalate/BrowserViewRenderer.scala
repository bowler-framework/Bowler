package org.bowlerframework.view.scalate

import org.bowlerframework.view.ViewRenderer
import org.bowlerframework.{Response, Request}
import org.bowlerframework.exception.{ValidationException, HttpException}
import org.bowlerframework.model.ViewModelBuilder
import collection.mutable.MutableList

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 20/04/2011
 * Time: 23:02
 * To change this template use File | Settings | File Templates.
 */

trait BrowserViewRenderer extends ViewRenderer{
  def onError(request: Request, response: Response, exception: Exception) = {
    response.setContentType("text/html")
    if (classOf[HttpException].isAssignableFrom(exception.getClass)) {
      if (exception.isInstanceOf[ValidationException]) {
        val validations = exception.asInstanceOf[ValidationException]
        request.getSession.setErrors(validations.errors)
        if (request.getSession.getLastGetPath != None)
          response.sendRedirect(request.getSession.getLastGetPath.get)
      } else {
        val http = exception.asInstanceOf[HttpException]
        response.sendError(http.code)
        // TODO
        // render error pages?
      }

    } else {
      throw exception
    }
  }

  def renderView(request: Request, response: Response, models: Seq[Any]) = {
    response.setContentType("text/html")
    val validated = request.getSession.getValidatedModel
    var tempModel = models
    if (validated != None && validated.get.size > 0)
      tempModel = tempModel ++ validated.get

    val model = ViewModelBuilder(tempModel)
    if (request.getSession.getErrors != None) {
      val list = new MutableList[String]
      request.getSession.getErrors.get.foreach(f => list += f._2)
      model += "validationErrors" -> list.toList
    }
    try{
      request.getSession.resetValidations
    }catch{
      case e: IllegalStateException => println("IllegalStateException (no session to reset): " +  e) // ignore, there is no active session, hence nothing to reset
    }
    render(request, response, model.toMap)
  }

  protected def render(request: Request, response: Response, model: Map[String, Any])
}