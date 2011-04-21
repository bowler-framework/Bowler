package org.bowlerframework.extractors

import org.bowlerframework.Request
import util.matching.Regex


class UriMatches[T](item: T, uri: Regex) {

  def unapply(request: Request): Option[T] = {
    if (uri.pattern.matcher(request.getPath).matches)
      return Some(item)
    else return None
  }

}