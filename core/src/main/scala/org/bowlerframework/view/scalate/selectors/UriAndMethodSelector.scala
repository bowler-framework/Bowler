package org.bowlerframework.view.scalate.selectors

import util.matching.Regex
import org.bowlerframework.{HttpMethod, Request}


class UriAndMethodSelector[T](item: T, method: HttpMethod, uri: Regex) extends UriSelector[T](item, uri){
  override def find(request: Request): Option[T] ={
    if(request.getMethod == method)
      return super.find(request)
    else return None
  }
}