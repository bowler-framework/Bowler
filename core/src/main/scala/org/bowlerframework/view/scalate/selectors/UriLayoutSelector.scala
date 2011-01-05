package org.bowlerframework.view.scalate.selectors

import util.matching.Regex
import org.bowlerframework.Request
import org.bowlerframework.view.scalate.Layout

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 05/01/2011
 * Time: 22:37
 * To change this layout use File | Settings | File Templates.
 */

class UriLayoutSelector(layout: Layout, uri: Regex) extends LayoutSelector{
  def layout(request: Request): Option[Layout] = {
    if(uri.pattern.matcher(request.getPath).matches)
      return Some(layout)
    else return None
  }
}