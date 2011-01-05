package org.bowlerframework.view.scalate.selectors

import util.matching.Regex
import org.bowlerframework.view.scalate.Layout
import org.bowlerframework.{Request, HTTP}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 05/01/2011
 * Time: 22:38
 * To change this layout use File | Settings | File Templates.
 */

class UriAndMethodLayoutSelector(layout: Layout, method: HTTP.Method, uri: Regex) extends UriLayoutSelector(layout, uri){
  override def layout(request: Request): Option[Layout] ={
    if(request.getMethod == method)
      return super.layout(request)
    else return None
  }
}