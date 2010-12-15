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
case class MappedPath(path: String, isRegex: Boolean = false)

object RequestScope {
  private val _response = new DynamicVariable[Response](null)
  private val _request = new DynamicVariable[Request](null)
  private val _mappedPath = new DynamicVariable[MappedPath](null)

  def response = _response value
  def request = _request value
  def mappedPath = _mappedPath value

  def executeRoute(mappedPath: MappedPath, requestScope: RequestScope, function: => Unit) = {
    _request.withValue(requestScope.request) {
      _response.withValue(requestScope.response) {
        _mappedPath.withValue(mappedPath) {
          try {
            function
            // add last completed path here
          } catch {
            case validationException => {
              //do nothing, this should be handled by the Validations trait onValidationErrors implementation
            }
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



