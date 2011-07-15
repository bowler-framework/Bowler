package org.bowlerframework.view

import org.bowlerframework.{Response, Request}
import util.matching.Regex.Match

/**
 * Created by IntelliJ IDEA.
 * User: noel kennedy
 * Date: 15/07/11
 * Time: 13:16
 * To change this template use File | Settings | File Templates.
 */

class JsonpViewRenderer(nameOfCallbackParameter: String) extends JsonViewRenderer {

  private def jsonWithPadding(renderJson: => Unit, request: Request, response: Response):Unit = {
    request.getStringParameter(nameOfCallbackParameter) match {
      case None => renderJson
      case Some(callback) => {
        response.getWriter.write(callback + "(")
        renderJson
        response.getWriter.write(")")
        response.setHeader("Content-Type", "application/javascript")
      }
    }
  }

  override def renderView(request : Request, response : Response, models : Seq[scala.Any]) : Unit = {
    jsonWithPadding(super.renderView(request, response, models), request, response)
  }

  override def renderView(request : Request, response : Response) : Unit = {
    jsonWithPadding(super.renderView(request, response), request, response)
  }
}

object JsonpViewRenderer {

  //the key of the jscript function in the query parameters
  //jquery defaults this to callback, so we follow their lead but it can be changed if necessary
  var nameOfCallbackParameter = "callback"
  def apply() = new JsonpViewRenderer(nameOfCallbackParameter)
}