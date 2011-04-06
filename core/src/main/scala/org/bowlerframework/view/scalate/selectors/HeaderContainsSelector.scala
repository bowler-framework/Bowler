package org.bowlerframework.view.scalate.selectors

import org.bowlerframework.Request


class HeaderContainsSelector[T](item: T, headers: Map[String, String]) extends RequestSelector[T] {
  def find(request: Request): Option[T] = {
    var conditionsMet = true
    headers.iterator.foreach(tup => {
      try {
        if (!request.getHeader(tup._1).contains(tup._2))
          conditionsMet = false
      } catch {
        case e: NoSuchElementException => {
          conditionsMet = false
        }
      }

    })
    if (!conditionsMet) return None
    else return Some(item)
  }
}