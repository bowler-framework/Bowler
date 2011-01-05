package org.bowlerframework.view.scalate.selectors

import org.bowlerframework.view.scalate.Layout
import org.bowlerframework.Request
import util.matching.Regex

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 05/01/2011
 * Time: 21:58
 * To change this layout use File | Settings | File Templates.
 */

class HeaderLayoutSelector(layout: Layout, headerSelectors: Map[String, Regex]) extends LayoutSelector{

  def layout(request: Request): Option[Layout] ={
    var conditionsMet = true
    headerSelectors.iterator.foreach(tup =>{
      if(!tup._2.pattern.matcher(request.getHeader(tup._1)).matches)
        conditionsMet = false
    })
    if(!conditionsMet) return None
    else return Some(layout)
  }
}