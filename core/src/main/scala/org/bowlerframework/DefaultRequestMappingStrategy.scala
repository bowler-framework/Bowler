package org.bowlerframework

import model.{RequestMapper, JsonRequestMapper, DefaultRequestMapper}
import collection.mutable.HashMap

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 25/12/2010
 * Time: 00:06
 * To change this template use File | Settings | File Templates.
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