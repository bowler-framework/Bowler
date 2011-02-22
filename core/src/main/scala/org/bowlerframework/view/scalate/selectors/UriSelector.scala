package org.bowlerframework.view.scalate.selectors

import org.bowlerframework.Request
import util.matching.Regex


class UriSelector[T](item: T, uri: Regex) extends RequestSelector[T]{

  def find(request: Request): Option[T] = {
    if(uri.pattern.matcher(request.getPath).matches)
      return Some(item)
    else return None
  }

}