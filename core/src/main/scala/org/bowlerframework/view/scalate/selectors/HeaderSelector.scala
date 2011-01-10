package org.bowlerframework.view.scalate.selectors

import util.matching.Regex
import org.bowlerframework.Request

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 07/01/2011
 * Time: 18:35
 * To change this template use File | Settings | File Templates.
 */

class HeaderSelector[T](item: T, headerSelectors: Map[String, Regex]) extends RequestSelector[T]{

  def find(request: Request): Option[T] ={
    var conditionsMet = true
    try{
      headerSelectors.iterator.foreach(tup =>{
        if(!tup._2.pattern.matcher(request.getHeader(tup._1)).matches)
          conditionsMet = false
      })
    }catch{
      case e: NoSuchElementException => {conditionsMet = false}
    }

    if(!conditionsMet) return None
    else return Some(item)
  }
}