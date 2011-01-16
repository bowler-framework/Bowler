package org.bowlerframework

import model.{RequestMapper, JsonRequestMapper, DefaultRequestMapper}
import collection.mutable.HashMap

/**
 * A default RequestMappingStrategy that will use a default HTTP Request mapper, or a JSON based mapper if the
 * Content-Type header of "application/json" is passed.
 */

class DefaultRequestMappingStrategy extends RequestMappingStrategy {
  private val requestMappers = new HashMap[String, RequestMapper]

  def addRequestMapper(contentType: String, mapper: RequestMapper) = requestMappers.put(contentType, mapper)

  def getRequestMapper(request: Request): RequestMapper = {
    if (request.getContentType.equals(None))
      return new DefaultRequestMapper
    else if (request.getContentType.get.toLowerCase.contains("application/json"))
      return new JsonRequestMapper
    else {
      try {
        return requestMappers(request.getContentType.get)
      } catch {
        case e: NoSuchElementException => return new DefaultRequestMapper
      }
    }
  }
}