package org.bowlerframework.view.scalate.selectors

import org.bowlerframework.Request
import org.bowlerframework.view.scalate.Layout

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 05/01/2011
 * Time: 21:58
 * To change this layout use File | Settings | File Templates.
 */

class HeaderContainsLayoutSelector(layout: Layout, headers: Map[String, String]) extends LayoutSelector{
  def layout(request: Request): Option[Layout] ={
    var conditionsMet = true
    headers.iterator.foreach(tup =>{
      if(!request.getHeader(tup._1).contains(tup._2))
        conditionsMet = false
    })
    if(!conditionsMet) return None
    else return Some(layout)
  }
}