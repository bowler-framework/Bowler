package org.bowlerframework

import exception.HttpException
import util.DynamicVariable


case class RequestScope(request: Request, response: Response)

object RequestScope {
  private val _response = new DynamicVariable[Response](null)
  private val _request = new DynamicVariable[Request](null)
  private val _mappedPath = new DynamicVariable[MappedPath](null)

  def response = _response value

  def request = _request value

  def mappedPath = _mappedPath value

  def executeRoute(mappedPath: MappedPath, requestScope: RequestScope, function: (Request, Response) => Unit) = {
    requestScope.request.setMappedPath(mappedPath)
    _request.withValue(requestScope.request) {
      _response.withValue(requestScope.response) {
        _mappedPath.withValue(mappedPath) {
          try {
            function(request, response)
          } catch {
            case e: Exception => {
              try{
                BowlerConfigurator.resolveViewRenderer(request).onError(request, response, e)
              }catch{
                case e: HttpException => {response.sendError(e.code)}
              }
            }
          }
        }
      }
    }
  }
}



