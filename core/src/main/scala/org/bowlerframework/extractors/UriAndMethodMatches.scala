package org.bowlerframework.extractors

import util.matching.Regex
import org.bowlerframework.{HttpMethod, Request}


class UriAndMethodMatches[T](item: T, method: HttpMethod, uri: Regex) extends UriMatches[T](item, uri) {
  override def unapply(request: Request): Option[T] = {
    if (request.getMethod == method)
      return super.unapply(request)
    else return None
  }
}