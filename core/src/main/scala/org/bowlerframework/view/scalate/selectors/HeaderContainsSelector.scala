package org.bowlerframework.view.scalate.selectors

import org.bowlerframework.Request

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 07/01/2011
 * Time: 18:33
 * To change this template use File | Settings | File Templates.
 */

class HeaderContainsSelector[T](item: T, headers: Map[String, String]) extends RequestSelector[T]{
  def find(request: Request): Option[T] ={
    var conditionsMet = true
    headers.iterator.foreach(tup =>{
      try{
        if(!request.getHeader(tup._1).contains(tup._2))
          conditionsMet = false
      }catch{
        case e: NoSuchElementException => {conditionsMet = false}
      }

    })
    if(!conditionsMet) return None
    else return Some(item)
  }
}