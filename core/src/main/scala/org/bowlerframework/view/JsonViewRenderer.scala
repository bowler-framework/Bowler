package org.bowlerframework.view

import json.BigDecimalSerializer
import org.bowlerframework.{Response, Request}
import net.liftweb.json.JsonAST._
import net.liftweb.json.Extraction._
import net.liftweb.json.Printer._
import org.bowlerframework.exception.HttpException
/**
 * JSON implementation of ViewRenderer - will take a Model or Models and render a JSON representation of said Model
 */
class JsonViewRenderer extends ViewRenderer {
  implicit val formats = net.liftweb.json.DefaultFormats + new BigDecimalSerializer

  def onError(request: Request, response: Response, exception: Exception) = {
    if (classOf[HttpException].isAssignableFrom(exception.getClass)) {
      val http = exception.asInstanceOf[HttpException]
      response.sendError(http.code)
      throw exception
    } else {
      throw exception
    }
  }

  def renderView(request: Request, response: Response, models: Seq[Any]) = {
    response.setContentType("application/json")
    if (models.size == 0) {
      response.setStatus(204)
    } else if (models.size == 1) {
      models.foreach(f => {
        response.getWriter.write(compact(render(decompose(f))))
      })
    } else {
      var json: JValue = null
      models.foreach(f => {
        val alias = getModelAlias(f)
        val value = getValue(f)
        if (json == null) json = new JField(alias, value)
        else json = json ++ JField(alias, value)
      })
      response.getWriter.write(compact(render(json)))
    }
  }


  /**
   * renders a no model view, in the case of JSON, this simply returns a HTTP 204 - No Content response.
   */
  def renderView(request: Request, response: Response) = {
    response.setContentType("application/json")
    response.setStatus(204)
  }

  private def getValue(any: Any): JValue = decompose(getModelValue(any))


}


case class ValidationError(key: String, message: String)