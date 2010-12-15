package org.bowlerframework

import util.DynamicVariable

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: Dec 10, 2010
 * Time: 1:47:28 AM
 * To change this template use File | Settings | File Templates.
 */

case class RequestScope(request: Request, response: Response)

object RequestScope {
  private val _response = new DynamicVariable[Response](null)
  private val _request = new DynamicVariable[Request](null)
  private val _mappedPath = new DynamicVariable[String](null)

  def response = _response value
  def request = _request value
  def mappedPath = _mappedPath value

  def executeRoute(mappedPath: String, requestScope: RequestScope, function: => Unit) = {
    _request.withValue(requestScope.request) {
      _response.withValue(requestScope.response) {
        _mappedPath.withValue(mappedPath) {
          try {
            function
          } catch {
            case e: Exception => {
              e.printStackTrace
              throw (e)
            }
          }
        }
      }
    }
  }
}