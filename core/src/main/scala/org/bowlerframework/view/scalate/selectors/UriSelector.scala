package org.bowlerframework.view.scalate.selectors

import org.bowlerframework.Request
import util.matching.Regex

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 07/01/2011
 * Time: 18:50
 * To change this template use File | Settings | File Templates.
 */

class UriSelector[T](item: T, uri: Regex) extends RequestSelector[T]{

  def find(request: Request): Option[T] = {
    if(uri.pattern.matcher(request.getPath).matches)
      return Some(item)
    else return None
  }

}