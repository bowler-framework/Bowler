package org.bowlerframework.view.scalate.selectors

import util.matching.Regex
import org.bowlerframework.{HTTP, Request}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 07/01/2011
 * Time: 18:55
 * To change this template use File | Settings | File Templates.
 */

class UriAndMethodSelector[T](item: T, method: HTTP.Method, uri: Regex) extends UriSelector[T](item, uri){
  override def find(request: Request): Option[T] ={
    if(request.getMethod == method)
      return super.find(request)
    else return None
  }
}