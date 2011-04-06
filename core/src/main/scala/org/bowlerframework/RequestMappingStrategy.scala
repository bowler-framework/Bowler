package org.bowlerframework

import model.RequestMapper

/**
 * Choses a strategy for mapping Requests.
 */

trait RequestMappingStrategy {
  def getRequestMapper(request: Request): RequestMapper
}